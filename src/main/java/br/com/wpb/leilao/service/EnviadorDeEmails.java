package br.com.wpb.leilao.service;

import br.com.wpb.leilao.model.Lance;
import br.com.wpb.leilao.model.Leilao;
import br.com.wpb.leilao.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class EnviadorDeEmails {

	// apenas simula o envio de um email...
	public void enviarEmailVencedorLeilao(Lance lanceVencedor) {
		Usuario vencedor = lanceVencedor.getUsuario();
		Leilao leilao = lanceVencedor.getLeilao();
		
		String email = String.format("Parabens {}! Voce venceu o leilao {} com o lance de R${}", vencedor.getNome(), leilao.getNome(), lanceVencedor.getValor());

		System.out.println(email);
	}

}
