package org.jugvale.call4papers.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Paper implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;

	@Column
	private String titulo;

	@Column
	private String descricao;

	@Column
	private String dataSubmissao;

	@Column
	private long nota;

	@Column
	private boolean aceito;

	@ManyToMany
	private Set<Autor> autores = new HashSet<Autor>();

	@ManyToOne
	private Evento evento;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
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
			return id.equals(((Paper) that).id);
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

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	public String getDataSubmissao() {
		return this.dataSubmissao;
	}

	public void setDataSubmissao(final String dataSubmissao) {
		this.dataSubmissao = dataSubmissao;
	}

	public long getNota() {
		return this.nota;
	}

	public void setNota(final long nota) {
		this.nota = nota;
	}

	public boolean getAceito() {
		return this.aceito;
	}

	public void setAceito(final boolean aceito) {
		this.aceito = aceito;
	}

	public Set<Autor> getAutores() {
		return this.autores;
	}

	public void setAutores(final Set<Autor> autores) {
		this.autores = autores;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		if (titulo != null && !titulo.trim().isEmpty())
			result += ", titulo: " + titulo;
		if (descricao != null && !descricao.trim().isEmpty())
			result += ", descricao: " + descricao;
		if (dataSubmissao != null && !dataSubmissao.trim().isEmpty())
			result += ", dataSubmissao: " + dataSubmissao;
		result += ", nota: " + nota;
		result += ", aceito: " + aceito;
		return result;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(final Evento evento) {
		this.evento = evento;
	}
}