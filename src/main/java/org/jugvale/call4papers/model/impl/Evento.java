package org.jugvale.call4papers.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.jugvale.call4papers.model.DefaultModel;
import org.jugvale.call4papers.model.builder.EventoBuilder;

@Entity
@XmlRootElement
@Getter @Setter 
@ToString(callSuper=true) 
@EqualsAndHashCode(callSuper=true)
public class Evento extends DefaultModel {

	private static final long serialVersionUID = 1L;

	@Column
	private String nome;

	@Column
	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;

	@Column
	private String local;

	@Column
	private String url;

	@Column
	private boolean aceitandoTrabalhos;

	public static EventoBuilder newEvento() {
		return new EventoBuilder();
	}
	
	public Evento() { }
	
	public Evento(String nome, String descricao, Date dataInicio, Date dataFim, String local, String url, boolean aceitandoTrabalhos) {
		this.nome = nome;
		this.descricao = descricao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.local = local;
		this.url = url;
		this.aceitandoTrabalhos = aceitandoTrabalhos;
	}
}