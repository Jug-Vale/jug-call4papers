package org.jugvale.call4papers.service.impl;

import javax.ejb.Stateless;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.service.ServiceAbstrato;

@Stateless
public class EventoService extends ServiceAbstrato<Evento> {

	@Override
	protected Class<Evento> retornaTipo() {
		return Evento.class;
	}

}
