/**
 * 
 */
package org.jugvale.cfp.model.builder;

import java.util.Date;

import org.jugvale.cfp.model.enums.Tipo;
import org.jugvale.cfp.model.impl.Autor;
import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.model.impl.Paper;


/**
 * @author Pedro Hos
 *
 */
public class PaperBuilder {

	private Paper paper;
	
	public static PaperBuilder newPapper() {
		return new PaperBuilder();
	}

	public PaperBuilder() {
		this.paper = new Paper();
	}

	public PaperBuilder(Tipo tipo) {
		this.paper = new Paper();
		paper.setTipo(tipo);
	}

	public Paper build() {
		return paper;
	}

	public PaperBuilder handsOn() {
		paper.setTipo(Tipo.HANDS_ON);
		return this;
	}
	
	public PaperBuilder palestra() {
		paper.setTipo(Tipo.PALESTRA);
		return this;
	}
	
	public PaperBuilder miniCurso() {
		paper.setTipo(Tipo.MINI_CURSO);
		return this;
	}
	
	public PaperBuilder comTitulo(String titulo) {
		paper.setTitulo(titulo);
		return this;
	}

	public PaperBuilder comDescricao(String descricao) {
		paper.setDescricao(descricao);
		return this;
	}

	public PaperBuilder submetidoNaData(Date dataSubmissao) {
		paper.setDataSubmissao(dataSubmissao);
		return this;
	}

	public PaperBuilder nota(long nota) {
		paper.setNota(nota);
		return this;
	}

	public PaperBuilder aceito() {
		paper.setAceito(true);
		return this;
	}
	
	public PaperBuilder naoAceito() {
		paper.setAceito(false);
		return this;
	}

	public PaperBuilder comAutor(Autor autor) {
		paper.getAutores().add(autor);
		return this;
	}

	public PaperBuilder noEvento(Evento evento) {
		paper.setEvento(evento);
		return this;
	}

}
