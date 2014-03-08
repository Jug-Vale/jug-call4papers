package org.jugvale.call4papers.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jugvale.call4papers.model.EntidadeAbstrata;

@Entity
@XmlRootElement
public class Evento extends EntidadeAbstrata {

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

	public Evento() {
		super();
	}

	public Evento(String nome, String descricao, Date dataInicio, Date dataFim, String local, String url, boolean aceitandoTrabalhos) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.local = local;
		this.url = url;
		this.aceitandoTrabalhos = aceitandoTrabalhos;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		if (id != null) {
			return id.equals(((Evento) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
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

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		if (nome != null && !nome.trim().isEmpty())
			result += ", nome: " + nome;
		if (descricao != null && !descricao.trim().isEmpty())
			result += ", descricao: " + descricao;
		if (local != null && !local.trim().isEmpty())
			result += ", local: " + local;
		if (url != null && !url.trim().isEmpty())
			result += ", url: " + url;
		result += ", aceitandoTrabalhos: " + aceitandoTrabalhos;
		return result;
	}
}