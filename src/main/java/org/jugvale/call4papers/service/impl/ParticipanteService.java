package org.jugvale.call4papers.service.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.jugvale.call4papers.model.impl.Participante;
import org.jugvale.call4papers.service.Service;

public class ParticipanteService extends Service<Participante> {

	public Participante buscaPorEmail(String email) {
		TypedQuery<Participante> query = em.createQuery(
				"SELECT p FROM Participante p WHERE p.email = :email",
				Participante.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
