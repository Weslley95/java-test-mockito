package br.com.wpb.leilao.service;

import br.com.wpb.leilao.dao.LeilaoDao;
import br.com.wpb.leilao.model.Lance;
import br.com.wpb.leilao.model.Leilao;
import br.com.wpb.leilao.model.Usuario;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinalizarLeilaoServiceTest {

    private FinalizarLeilaoService finalizarLeilaoService;

    @Mock
    private LeilaoDao leilaoDao;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.finalizarLeilaoService = new FinalizarLeilaoService(leilaoDao);
    }

    private List<Leilao> leilaoList() {
        List<Leilao> list = new ArrayList<>();

        Leilao leilao = new Leilao("Monitor", new BigDecimal("1500"), new Usuario("Ricardo"));

        Lance l1 = new Lance(new Usuario("Maria"), new BigDecimal("1600"));
        Lance l2 = new Lance(new Usuario("Lucas"), new BigDecimal("1800"));

        leilao.propoe(l1);
        leilao.propoe(l2);

        list.add(leilao);

        return list;
    }

    @Test
    void deveFinalizarUmLeilao() {
        List<Leilao> leiloes = leilaoList();

        Mockito.when(leilaoDao.buscarLeiloesExpirados()).thenReturn(leiloes);

        finalizarLeilaoService.finalizarLeiloesExpirados();

        Leilao leilao = leiloes.get(0);
        Assert.assertTrue(leilao.isFechado());
        Assert.assertEquals(new BigDecimal("1800"), leilao.getLanceVencedor().getValor());
        Mockito.verify(leilaoDao).salvar(leilao);
    }
}