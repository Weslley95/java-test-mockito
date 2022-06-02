package br.com.wpb.leilao.service;

import br.com.wpb.leilao.dao.PagamentoDao;
import br.com.wpb.leilao.model.Lance;
import br.com.wpb.leilao.model.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class GeradorDePagamento {

	@Autowired
	private PagamentoDao pagamentos;

	private Clock clock;

	@Autowired
	public GeradorDePagamento(PagamentoDao pagamentos, Clock clock) {
		this.pagamentos = pagamentos;
		this.clock = clock;
	}

	public void gerarPagamento(Lance lanceVencedor) {
		LocalDate vencimento = LocalDate.now(clock).plusDays(1);
		Pagamento pagamento = new Pagamento(lanceVencedor, proximoDiaUtil(vencimento));
		this.pagamentos.salvar(pagamento);
	}

	private LocalDate proximoDiaUtil(LocalDate localDate) {
		DayOfWeek diaSemana = localDate.getDayOfWeek();
		if (diaSemana.equals(DayOfWeek.SATURDAY)) {
			return localDate.plusDays(2);
		}

		return localDate;
	}
}
