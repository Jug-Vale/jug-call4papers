package org.jugvale.call4papers.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Inscricao;
import org.jugvale.call4papers.model.impl.Participante;
import org.jugvale.call4papers.service.Service;

public class EventoService extends Service<Evento> {

	public Inscricao inscreverParticipante(Evento evento,
			Participante participante) {
		Inscricao inscricao = new Inscricao(evento, participante, false,
				new Date());
		em.persist(inscricao);
		return inscricao;
	}

	public Inscricao buscaInscricao(Evento evento, Participante participante) {
		TypedQuery<Inscricao> query = em
				.createQuery("SELECT i FROM Inscricao i "
						+ "WHERE i.evento = :evento "
						+ "AND i.participante = :participante", Inscricao.class);
		query.setParameter("evento", evento);
		query.setParameter("participante", participante);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Inscricao> inscritosNoEvento(Evento evento) {
		TypedQuery<Inscricao> query = em.createQuery(
				"SELECT i FROM Inscricao i WHERE i.evento = :evento",
				Inscricao.class);
		query.setParameter("evento", evento);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}

	}

}
