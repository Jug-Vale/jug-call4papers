package org.jugvale.cfp.model.impl;

import static javax.persistence.FetchType.EAGER;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.jugvale.cfp.model.DefaultModel;
import org.jugvale.cfp.model.enums.Tipo;

/**
 * 
 * A classe de modelo para o Paper <br>
 * Aqui colocamos infomações a respeito do Paper para o Evento.
 * 
 * @author william
 * 
 */
@Entity
public class Paper extends DefaultModel {

	private static final long serialVersionUID = 1L;

	@Column
	private String titulo;

	@Column(length=1000)
	private String descricao;

	@Column
	private Date dataSubmissao = new Date();

	@Column
	private long nota;

	@Column
	private boolean aceito;

	@ManyToMany(fetch = EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "AUTOR_PAPER", joinColumns = { @JoinColumn(name = "PAPER_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTOR_ID") })
	private Set<Autor> autores = new HashSet<Autor>();

	@ManyToOne
	private Evento evento;

	@Column
	@Enumerated(EnumType.STRING)
	private Tipo tipo;


	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataSubmissao() {
		return dataSubmissao;
	}

	public void setDataSubmissao(Date dataSubmissao) {
		this.dataSubmissao = dataSubmissao;
	}

	public long getNota() {
		return nota;
	}

	public void setNota(long nota) {
		this.nota = nota;
	}

	public boolean isAceito() {
		return aceito;
	}

	public void setAceito(boolean aceito) {
		this.aceito = aceito;
	}

	public Set<Autor> getAutores() {
		return autores;
	}

	public void setAutores(Set<Autor> autores) {
		this.autores = autores;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}