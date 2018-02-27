package org.jugvale.cfp.service.impl;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.jugvale.cfp.model.impl.Participante;
import org.jugvale.cfp.service.Service;

@Default
@Any
public class ParticipanteService extends Service<Participante> {

	public Participante buscaPorEmail(String email) {
		TypedQuery<Participante> query = em.createQuery("SELECT p FROM Participante p WHERE p.email = :email",
				Participante.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
