package org.jugvale.cfp.model;

import java.util.Date;

import javax.json.bind.annotation.JsonbDateFormat;
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
	@JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ") 
	public Date dataInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ") 
	public Date dataFim;

	@Column
	public String local;

	@Column
	public String url;

	@Column
	public boolean aceitandoTrabalhos;
	
	@Column
	public boolean inscricoesAbertas;
	
	public static Evento update(Evento existing, Evento evento) {
	    existing.descricao = evento.descricao;
	    existing.dataInicio = evento.dataInicio;
	    existing.dataFim = evento.dataFim;
	    existing.local = evento.local;
	    existing.url = evento.url;
	    existing.aceitandoTrabalhos = evento.aceitandoTrabalhos;
	    existing.inscricoesAbertas = evento.inscricoesAbertas;
	    existing.persist();
        return existing;
	}
	
}