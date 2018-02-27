package org.jugvale.cfp.model.builder;

import java.util.Date;

import org.jugvale.cfp.model.impl.Evento;

/**
 * @author Pedro Hos
 *
 */
public class EventoBuilder {

	private Evento evento;
	
	public EventoBuilder() {
		this.evento = new Evento();
	}
	
	public Evento build() {
		return evento;
	}
	
	public EventoBuilder comNome (String nome) {
		evento.setNome(nome);
		return this;
	}
	
	public EventoBuilder comDescricao(String descricao) {
		evento.setDescricao(descricao);
		return this;
	}
	
	public EventoBuilder comDataInicio(Date data) {
		evento.setDataInicio(data);
		return this;
	}
	
	public EventoBuilder comDataFim(Date data) {
		evento.setDataFim(data);
		return this;
	}
	
	public EventoBuilder noLocal(String local) {
		evento.setLocal(local);
		return this;
	}
	
	public EventoBuilder comSite(String url) {
		evento.setUrl(url);
		return this;
	}
	
	public EventoBuilder aceitandoTrabalhos() {
		evento.setAceitandoTrabalhos(true);
		return this;
	}
	
	public EventoBuilder naoAceitandoTrabalhos() {
		evento.setAceitandoTrabalhos(true);
		return this;
	}
	
	public EventoBuilder inscricoesAbertas() {
		evento.setInscricoesAbertas(true);
		return this;
	}
	
	public EventoBuilder inscricoesFechadas() {
		evento.setInscricoesAbertas(false);
		return this;
	}
	
	
}
