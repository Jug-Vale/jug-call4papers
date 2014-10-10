package org.jugvale.call4papers.rest.voto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

/**
 * 
 * Armazena os votos por IP. Dessa forma não é possível votar várias vezes com o browser.
 * 
 * TODO: Limpar o mapa periodicamente ou teremos um leak aqui.
 * 
 * @author william
 *
 */
@Singleton
public class VotosSalvos {

	/**
	 * Esse mapa contém o IP de quem já votou e a lista de id papers já votados.
	 */
	private Map<String, Set<Long>> votosSalvos;

	@PostConstruct
	private void inicializa() {
		votosSalvos = new HashMap<>();
	}

	public boolean ipJaVotouNoPaper(String ip, Long idPaper) {
		Set<Long> papersVotados = votosSalvos.getOrDefault(ip, new HashSet<>());
		boolean paperVotadoParaIp = papersVotados.stream().anyMatch(idPaper::equals);
		if(!paperVotadoParaIp) {
			papersVotados.add(idPaper);
			votosSalvos.put(ip, papersVotados);
		}
		return paperVotadoParaIp;		
	}

}
