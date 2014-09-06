package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.rest.EventoResource;
import org.jugvale.call4papers.service.impl.EventoService;
import org.jugvale.call4papers.service.impl.PaperService;

public class EventoResourceImpl implements EventoResource {

	@Inject
	EventoService eventoService;
	
	@Inject 
	PaperService paperService;

	public Response criar(Evento evento) {
		eventoService.salvar(evento);
		return recursoCriado(EventoResource.class, evento.getId());
	}

	@Override
	public Response apagaPorId(Long id) {
		
		try {
			Evento evento = lanca404SeNulo(eventoService.buscarPorId(id), id);
			eventoService.remover(evento);
			return Response.ok().build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response buscaPorId(Long id) {
		
		try {
			Evento evento = eventoService.buscarPorId(id);
			return Response.ok(lanca404SeNulo(evento, id)).build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response listarTodos() {
		return Response.ok(eventoService.buscaTodos()).build();
	}

	@Override
	public Response atualizar(long id, Evento novoEvento) {
		
		try {
			lanca404SeNulo(buscaPorId(id), id);
			novoEvento.setId(id);
			eventoService.atualizar(novoEvento);
			return Response.ok().build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response listaPapersPorEvento(Long eventoId) {
		
		try {
			Evento evento = eventoService.buscarPorId(eventoId);
			lanca404SeNulo(evento, eventoId);
			List<Paper> papers = paperService.listarPapersPorEvento(evento);
			return Response.ok(papers).build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
		
	}

}