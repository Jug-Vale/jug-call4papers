package org.jugvale.cfp.client.shared.model;

import java.util.Date;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jugvale.cfp.model.DefaultModel;

@Portable
public class Inscricao extends DefaultModel {

	private static final long serialVersionUID = 1L;

	private Evento evento;

	private Participante participante;

	private boolean compareceu;

	private Date data;

	public Inscricao() {
	}

	public Inscricao(Evento evento, Participante participante, boolean compareceu, Date data) {
		super();
		this.evento = evento;
		this.participante = participante;
		this.compareceu = compareceu;
		this.data = data;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public boolean isCompareceu() {
		return compareceu;
	}

	public void setCompareceu(boolean compareceu) {
		this.compareceu = compareceu;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
