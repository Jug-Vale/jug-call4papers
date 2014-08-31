package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.rest.AutorResource;
import org.jugvale.call4papers.rest.EventoResource;
import org.jugvale.call4papers.service.impl.EventoService;

public class EventoResourceImpl implements EventoResource {

	@Inject
	EventoService service;

	public Response criar(Evento evento) {
		service.salvar(evento);
		return recursoCriado(AutorResource.class, evento.getId());
	}

	@Override
	public void apagaPorId(@PathParam("id") Long id) {
		Evento evento = service.buscarPorId(id);
		service.remover(lanca404SeNulo(evento, id));
	}

	@Override
	public Evento buscaPorId(@PathParam("id") Long id) {
		Evento evento = service.buscarPorId(id);
		return lanca404SeNulo(evento, id);
	}

	@Override
	public List<Evento> listarTodos() {
		return service.buscaTodos();
	}

	@Override
	public void atualizar(@PathParam("id") long id, Evento novoEvento) {
		lanca404SeNulo(buscaPorId(id), id);
		novoEvento.setId(id);
		service.atualizar(novoEvento);
	}

}
