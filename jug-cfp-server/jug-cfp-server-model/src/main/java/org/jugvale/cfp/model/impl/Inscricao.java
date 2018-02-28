package org.jugvale.cfp.model.impl;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.jugvale.cfp.model.DefaultModel;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Inscricao extends DefaultModel{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Evento evento;
	
	/**
	 * O participante será criado em cascata. <br /> 
	 * No entanto, se for repetido (e-mail já existe no banco), teremos error. <br />
	 * Dessa forma temos que verificar existência do participante e não deixar qualquer transiente ser persistido.
	 */
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Participante participante;
	
	@Column
	private boolean compareceu;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yy HH:00", timezone="GMT-3")
	@Column
	private Date data;
	
	public Inscricao() {}
	
	public Inscricao(Evento evento, Participante participante,
			boolean compareceu, Date data) {
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
