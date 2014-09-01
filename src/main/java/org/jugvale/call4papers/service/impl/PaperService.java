package org.jugvale.call4papers.service.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.service.ServiceAbstrato;

@Stateless
public class PaperService extends ServiceAbstrato<Paper> {

	@Override
	protected Class<Paper> retornaTipo() {
		return Paper.class;
	}

	public List<Paper> listarPapersPorAutor(Autor autor) {
		@SuppressWarnings("unchecked")
		List<Paper> papers = (List<Paper>) em
				.createQuery(
						"SELECT p FROM Paper p INNER JOIN p.autores a WHERE a.id = ?1")
				.setParameter(1, autor.getId())
				.getResultList();
		return papers;
	}

	public List<Paper> listarPapersPorEvento(Evento evento) {
		@SuppressWarnings("unchecked")
		List<Paper> papers = (List<Paper>) em
				.createQuery(
						"SELECT DISTINCT p FROM Paper p WHERE p.evento.id = ?1")
				.setParameter(1, evento.getId()).getResultList();
		return papers;
	}

}