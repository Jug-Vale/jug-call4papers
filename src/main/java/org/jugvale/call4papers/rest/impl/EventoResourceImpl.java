package org.jugvale.call4papers.rest.impl;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.rest.EventoResource;
import org.jugvale.call4papers.service.impl.EventoService;

public class EventoResourceImpl implements EventoResource {

	@Inject
	EventoService eventos;
	
	public Response criar(Evento evento) {
		return eventos.novo(evento);
	}

	@Override
	public Response apagaPorId(Long id) {
		return eventos.remover(id);
	}

	@Override
	public Response buscaPorId(Long id) {
		return eventos.comId(id);
	}

	@Override
	public Response listarTodos() {
		return eventos.todos();
	}

	@Override
	public Response atualizar(long id, Evento evento) {
		return eventos.atualizar(id, evento);
	}

	@Override
	public Response listaPapersPorEvento(Long eventoId) {
		return eventos.papersParaOEvento(eventoId);
	}

}