package org.jugvale.call4papers.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jugvale.call4papers.model.DefaultModel;

@Entity
@XmlRootElement
@SuppressWarnings("serial")
public class Evento extends DefaultModel {

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
	
	public static class Builder {
		private Evento evento;
		
		public Builder() {
			this.evento = new Evento();
		}
		
		public Evento build() {
			return evento;
		}
		
		public Builder comNome (String nome) {
			evento.nome = nome;
			return this;
		}
		
		public Builder comDescricao(String descricao) {
			evento.descricao = descricao;
			return this;
		}
		
		public Builder comDataInicio(Date data) {
			evento.dataInicio = data;
			return this;
		}
		
		public Builder comDataFim(Date data) {
			evento.dataFim = data;
			return this;
		}
		
		public Builder noLocal(String local) {
			evento.local = local;
			return this;
		}
		
		public Builder comSite(String url) {
			evento.url = url;
			return this;
		}
		
		public Builder aceitandoTrabalhos() {
			evento.aceitandoTrabalhos = true;
			return this;
		}
		
		public Builder naoAceitandoTrabalhos() {
			evento.aceitandoTrabalhos = true;
			return this;
		}
	}
	
	public String getNome() {
		return this.nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(final Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(final Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getLocal() {
		return this.local;
	}

	public void setLocal(final String local) {
		this.local = local;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public boolean getAceitandoTrabalhos() {
		return this.aceitandoTrabalhos;
	}

	public void setAceitandoTrabalhos(final boolean aceitandoTrabalhos) {
		this.aceitandoTrabalhos = aceitandoTrabalhos;
	}
	
}