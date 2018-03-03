package org.jugvale.cfp.service;

import org.jugvale.cfp.model.impl.Participante;

public interface ParticipanteService extends Service<Participante> {

	Participante buscaPorEmail(String email);

}
