package org.jugvale.cfp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import io.quarkus.hibernate.orm.panache.PanacheEntity;


@Entity
public class Evento extends PanacheEntity {

	@Column
	public String nome;

	@Column(length=1000)
	public String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	public Date dataInicio;

	@Temporal(TemporalType.TIMESTAMP)
	public Date dataFim;

	@Column
	public String local;

	@Column
	public String url;

	@Column
	public boolean aceitandoTrabalhos;
	
	@Column
	public boolean inscricoesAbertas;	
	
}