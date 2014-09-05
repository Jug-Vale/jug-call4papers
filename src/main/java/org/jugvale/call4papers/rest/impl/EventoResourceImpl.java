package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.rest.EventoResource;
import org.jugvale.call4papers.service.impl.EventoService;
import org.jugvale.call4papers.service.impl.PaperService;

public class EventoResourceImpl implements EventoResource {

	@Inject
	EventoService service;
	
	@Inject 
	PaperService paperService;

	public Response criar(Evento evento) {
		service.salvar(evento);
		return recursoCriado(EventoResource.class, evento.getId());
	}

	@Override
	public Response apagaPorId(Long id) {
		Evento evento = lanca404SeNulo(service.buscarPorId(id), id);
		service.remover(evento);
		return Response.ok().build();
	}

	@Override
	public Response buscaPorId(Long id) {
		Evento evento = service.buscarPorId(id);
		return Response.ok(lanca404SeNulo(evento, id)).build();
	}

	@Override
	public Response listarTodos() {
		return Response.ok(service.buscaTodos()).build();
	}

	@Override
	public Response atualizar(long id, Evento novoEvento) {
		lanca404SeNulo(buscaPorId(id), id);
		novoEvento.setId(id);
		service.atualizar(novoEvento);
		return Response.ok().build();
	}

	@Override
	public Response listaPapersPorEvento(Long eventoId) {
		Evento e = (Evento) buscaPorId(eventoId).getEntity();
		return Response.ok(paperService.listarPapersPorEvento(e)).build();
	}

}