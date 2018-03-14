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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.jugvale.cfp.model.DefaultModel;
import org.jugvale.cfp.model.config.Views;
import org.jugvale.cfp.model.enums.Tipo;

import com.fasterxml.jackson.annotation.JsonView;

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
	@JsonView(Views.ResumoPaper.class)
	@NotNull(message = "Título da proposta não pode ser nulo")
	@NotEmpty(message = "Título da proposta não pode ser vazio")
	private String titulo;

	@Column(length = 1000)
	@JsonView(Views.ResumoPaper.class)
	@NotEmpty(message = "A proposta precisa de uma descrição!")
	@NotNull(message = "A descrição da proposta não pode ser nula")
	@Length(min = 50, max = 1000, message = "O texto da descrição deve conter entre 50 e 1000 caracteres")
	private String descricao;

	@Column
	private Date dataSubmissao = new Date();

	@Column
	@JsonView(Views.ResumoPaper.class)
	private long nota;

	@Column
	private boolean aceito;

	@ManyToMany(fetch = EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "AUTOR_PAPER", joinColumns = { @JoinColumn(name = "PAPER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "AUTOR_ID") })
	@JsonView(Views.ResumoPaper.class)
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