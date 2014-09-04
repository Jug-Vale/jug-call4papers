package org.jugvale.call4papers.model.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.jugvale.call4papers.model.DefaultModel;
import org.jugvale.call4papers.model.builder.PaperBuilder;

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
@EqualsAndHashCode(callSuper = true) @ToString(callSuper=true)
@Getter @Setter
public class Paper extends DefaultModel {

	private static final long serialVersionUID = 1L;

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

	@ManyToMany(fetch=FetchType.EAGER)
	private Set<Autor> autores = new HashSet<Autor>();

	@ManyToOne
	private Evento evento;

	public static PaperBuilder newPapper() {
		return new PaperBuilder();
	}
	
}