package org.jugvale.cfp.client.shared.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jugvale.cfp.model.DefaultModel;
import org.jugvale.cfp.model.enums.Tipo;

@Portable
public class Paper extends DefaultModel {

	private static final long serialVersionUID = 1L;

	private String titulo;

	private String descricao;

	private Date dataSubmissao = new Date();

	private long nota;

	private boolean aceito;

	private Set<Autor> autores = new HashSet<Autor>();

	private Evento evento;

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