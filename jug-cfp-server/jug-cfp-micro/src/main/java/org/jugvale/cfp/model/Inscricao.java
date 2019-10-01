package org.jugvale.cfp.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Inscricao extends PanacheEntity {
	
	
	@ManyToOne
	private Evento evento;
	
	/**
	 * O participante será criado em cascata. <br /> 
	 * No entanto, se for repetido (e-mail já existe no banco), teremos error. <br />
	 * Dessa forma temos que verificar existência do participante e não deixar qualquer transiente ser persistido.
	 */
	@ManyToOne(cascade=CascadeType.PERSIST)
	public Participante participante;
	
	@Column
	public boolean compareceu;
	
	@Column
	public Date data;
	
	public Inscricao() {}
	
}
