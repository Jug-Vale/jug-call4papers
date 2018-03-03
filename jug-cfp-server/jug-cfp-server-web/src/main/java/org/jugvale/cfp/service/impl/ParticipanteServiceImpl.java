package org.jugvale.cfp.service.impl;

import javax.enterprise.inject.Default;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.jugvale.cfp.model.impl.Participante;
import org.jugvale.cfp.service.AbstractService;
import org.jugvale.cfp.service.ParticipanteService;

@Default
public class ParticipanteServiceImpl extends AbstractService<Participante> implements ParticipanteService {

	@Override
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
