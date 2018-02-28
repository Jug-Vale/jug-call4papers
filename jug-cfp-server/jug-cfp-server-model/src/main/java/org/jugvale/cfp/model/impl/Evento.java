package org.jugvale.cfp.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jugvale.cfp.model.DefaultModel;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Evento extends DefaultModel {

	private static final long serialVersionUID = 1L;

	@Column
	private String nome;

	@Column(length=1000)
	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:00", timezone="GMT-3")
	private Date dataInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:00", timezone="GMT-3")
	private Date dataFim;

	@Column
	private String local;

	@Column
	private String url;

	@Column
	private boolean aceitandoTrabalhos;
	
	@Column
	private boolean inscricoesAbertas;	

	public Evento() { }

	public Evento(String nome, String descricao, Date dataInicio, Date dataFim,
			String local, String url, boolean aceitandoTrabalhos,
			boolean inscricoesAbertas) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.local = local;
		this.url = url;
		this.aceitandoTrabalhos = aceitandoTrabalhos;
		this.inscricoesAbertas = inscricoesAbertas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isAceitandoTrabalhos() {
		return aceitandoTrabalhos;
	}

	public void setAceitandoTrabalhos(boolean aceitandoTrabalhos) {
		this.aceitandoTrabalhos = aceitandoTrabalhos;
	}

	public boolean isInscricoesAbertas() {
		return inscricoesAbertas;
	}

	public void setInscricoesAbertas(boolean inscricoesAbertas) {
		this.inscricoesAbertas = inscricoesAbertas;
	}
	
}