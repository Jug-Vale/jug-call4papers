package org.jugvale.cfp.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.jugvale.cfp.model.dto.ResumoInscricao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Inscricao extends PanacheEntity {
	
	@ManyToOne
	public Evento evento;
	
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
	
	public static Inscricao of(Participante p, Evento e) {
	    Inscricao inscricao = new Inscricao();
	    inscricao.participante = p;
	    inscricao.evento = e;
	    return inscricao;
	}
	
	public static List<ResumoInscricao> porEvento(Evento evento) {
	    return Inscricao.find("evento", evento)
                        .stream()
                        .map(i -> (Inscricao) i)
                        .map(ResumoInscricao::of)
                        .collect(Collectors.toList());
	}
	
}
