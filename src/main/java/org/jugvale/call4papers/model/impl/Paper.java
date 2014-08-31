package org.jugvale.call4papers.model.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.jugvale.call4papers.model.DefaultModel;

/**
 * 
 * A classe de modelo para o Paper <br>
 * Aqui colocamos infomações a respeito do Paper para o Evento.
 * 
 * @author william
 * 
 */
@Entity
@XmlRootElement
@SuppressWarnings("serial")
public class Paper extends DefaultModel {

	@Column
	private String titulo;

	@Column
	private String descricao;

	@Column
	private Date dataSubmissao;

	@Column
	private long nota;

	@Column
	private boolean aceito;

	@ManyToMany
	private Set<Autor> autores = new HashSet<Autor>();

	@ManyToOne
	private Evento evento;

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

	public Date getDataSubmissao() {
		return dataSubmissao;
	}

	public void setDataSubmissao(Date dataSubmissao) {
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

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(final Evento evento) {
		this.evento = evento;
	}
}