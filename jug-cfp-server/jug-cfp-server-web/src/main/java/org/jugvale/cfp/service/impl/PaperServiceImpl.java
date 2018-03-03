package org.jugvale.cfp.service.impl;

import java.util.List;

import javax.enterprise.inject.Default;

import org.jugvale.cfp.model.impl.Autor;
import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.model.impl.Paper;
import org.jugvale.cfp.service.AbstractService;
import org.jugvale.cfp.service.PaperService;

@Default 
public class PaperServiceImpl extends AbstractService<Paper> implements PaperService {

	@Override
	public List<Paper> listarPapersPorAutor(Autor autor) {
		@SuppressWarnings("unchecked")
		List<Paper> papers = (List<Paper>) em
				.createQuery(
						"SELECT p FROM Paper p INNER JOIN p.autores a WHERE a.id = ?1")
				.setParameter(1, autor.getId()).getResultList();
		return papers;
	}

	@Override
	public List<Paper> listarPapersPorEvento(Evento evento) {
		@SuppressWarnings("unchecked")
		List<Paper> papers = (List<Paper>) em
				.createQuery(
						"SELECT DISTINCT p FROM Paper p WHERE p.evento.id = ?1")
				.setParameter(1, evento.getId()).getResultList();
		return papers;
	}

}
