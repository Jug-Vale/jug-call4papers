package org.jugvale.cfp.client.shared.model;

import java.io.Serializable;
import java.util.Date;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class Evento implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;

	private String nome;

	private String descricao;

	private Date dataInicio;

	private Date dataFim;

	private String local;

	private String url;

	private boolean aceitandoTrabalhos;
	
	private boolean inscricoesAbertas;	

	public Evento() { }

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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}