package org.jugvale.call4papers.rest.impl;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.rest.RestAbstrato;
import org.jugvale.call4papers.service.impl.EventoService;

@Path("/eventos")
public class EventoEndpoint extends RestAbstrato<Evento> {

	@Inject
	EventoService service;

	@Override
	public Response criar(Evento entidade) {
		service.salvar(entidade);
		return Response.created(
				UriBuilder.fromResource(EventoEndpoint.class)
						.path(String.valueOf(entidade.getId())).build())
				.build();
	}

	@Override
	public void apagaPorId(@PathParam("id") Long id) {
		Evento evento = buscaPorId(id);
		service.remover(verificaSeEhNulo(evento, id));
	}

	@Override
	public Evento buscaPorId(@PathParam("id") Long id) {
		Evento evento = service.buscarPorId(id);
		return verificaSeEhNulo(evento, id);
	}

	@Override
	public List<Evento> listarTodos() {
		return service.buscaTodos();
	}

	@Override
	public void atualizar(@PathParam("id") long id, Evento evento) {
		verificaSeEhNulo(buscaPorId(id), id);
		evento.setId(id);
		service.atualizar(evento);
	}

}