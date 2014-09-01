package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.rest.AutorResource;
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
		return recursoCriado(AutorResource.class, evento.getId());
	}

	@Override
	public void apagaPorId(Long id) {
		Evento evento = service.buscarPorId(id);
		service.remover(lanca404SeNulo(evento, id));
	}

	@Override
	public Evento buscaPorId(Long id) {
		Evento evento = service.buscarPorId(id);
		return lanca404SeNulo(evento, id);
	}

	@Override
	public List<Evento> listarTodos() {
		return service.buscaTodos();
	}

	@Override
	public void atualizar(long id, Evento novoEvento) {
		lanca404SeNulo(buscaPorId(id), id);
		novoEvento.setId(id);
		service.atualizar(novoEvento);
	}

	@Override
	public List<Paper> listaPapersPorEvento(Long eventoId) {
		Evento e = buscaPorId(eventoId);
		return paperService.listarPapersPorEvento(e);
	}

}