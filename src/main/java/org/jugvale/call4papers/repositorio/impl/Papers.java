package org.jugvale.call4papers.repositorio.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.repositorio.Repositorio;

@Stateless
public class Papers extends Repositorio<Paper> {

	public List<Paper> papersDoAutor(Autor autor) {
		
		String jpql = "SELECT p FROM Paper p INNER JOIN p.autores a WHERE a.id = :id";
		
		TypedQuery<Paper> query = em.createQuery(jpql, Paper.class);
		query.setParameter("id", autor.getId());

		return query.getResultList();
	}

	public List<Paper> papersParaOEvento(Evento evento) {
		
		String jpql = "SELECT DISTINCT p FROM Paper p WHERE p.evento.id = :id";
		
		TypedQuery<Paper> query = em.createQuery(jpql, Paper.class);
		query.setParameter("id", evento.getId());

		return query.getResultList();
	}

}