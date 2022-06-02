package br.com.wpb.leilao.service;

import br.com.wpb.leilao.dao.PagamentoDao;
import br.com.wpb.leilao.model.Lance;
import br.com.wpb.leilao.model.Leilao;
import br.com.wpb.leilao.model.Pagamento;
import br.com.wpb.leilao.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GeradorDePagamentoTest {

    private GeradorDePagamento geradorDePagamento;

    @Mock
    private PagamentoDao pagamentoDao;

    @Captor
    private ArgumentCaptor<Pagamento> captor;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.geradorDePagamento = new GeradorDePagamento(pagamentoDao);
    }

    private Leilao leilao() {
        Leilao leilao = new Leilao("Monitor", new BigDecimal("1500"), new Usuario("Ricardo"));
        Lance l = new Lance(new Usuario("Lucas"), new BigDecimal("1800"));
        leilao.propoe(l);
        leilao.setLanceVencedor(l);

        return leilao;
    }

    @Test
    void deveCriarPagamento() {
        Leilao l = leilao();
        Lance lanceVencedor = l.getLanceVencedor();
        geradorDePagamento.gerarPagamento(lanceVencedor);

        Mockito.verify(pagamentoDao).salvar(captor.capture());

        Pagamento pagamento = captor.getValue();

        assertEquals(LocalDate.now().plusDays(1), pagamento.getVencimento());
        assertEquals(lanceVencedor.getValor(), pagamento.getValor());
        assertFalse(pagamento.getPago());
        assertEquals(lanceVencedor.getUsuario(), pagamento.getUsuario());
        assertEquals(l, pagamento.getLeilao());
    }
}