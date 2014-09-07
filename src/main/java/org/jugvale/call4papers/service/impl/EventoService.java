package org.jugvale.call4papers.service.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.repositorio.impl.Eventos;
import org.jugvale.call4papers.repositorio.impl.Papers;
import org.jugvale.call4papers.rest.EventoResource;
import org.jugvale.call4papers.service.Service;

public class EventoService implements Service<Evento> {

	@Inject
	Eventos eventos;
	
	@Inject 
	Papers papers;
	
	@Override
	public Response novo(Evento evento) {
		eventos.novo(evento);
		return recursoCriado(EventoResource.class, evento.getId());
	}

	@Override
	public Response remover(Long id) throws WebApplicationException {
		try {
			
			Evento evento = lanca404SeNulo(eventos.comId(id), id);
			eventos.remover(evento);
			return Response.ok().build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response comId(Long id) throws WebApplicationException {
		try {
			
			Evento evento = eventos.comId(id);
			return Response.ok( lanca404SeNulo(evento, id) ).build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response todos() {
		return Response.ok(eventos.todos()).build();
	}

	@Override
	public Response atualizar(Long id, Evento evento) throws WebApplicationException {
		try {
			
			lanca404SeNulo(eventos.comId(id), id);
			evento.setId(id);
			eventos.atualizar(evento);
			return Response.ok().build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}
	
	public Response papersParaOEvento(Long eventoId) {
		try {
			
			Evento evento = eventos.comId(eventoId);
			lanca404SeNulo(evento, eventoId);
			List<Paper> papersParaEsteEvento = papers.papersParaOEvento(evento);
			return Response.ok(papersParaEsteEvento).build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

}
