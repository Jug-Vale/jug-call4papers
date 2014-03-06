package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jugvale.call4papers.model.Evento;
import org.jugvale.call4papers.service.impl.EventoService;

/**
 * 
 */
@Stateless
@Path("/eventos")
public class EventoEndpoint {

	@Inject
	EventoService service;

	@POST
	@Consumes("application/json")
	public Response create(Evento entidade) {
		service.salvar(entidade);
		return Response.created(
				UriBuilder.fromResource(EventoEndpoint.class)
						.path(String.valueOf(entidade.getId())).build())
				.build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteById(@PathParam("id") Long id) {
		Evento evento = findById(id);
		service.remover(verificaSeEventoEhNulo(evento, id));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Evento findById(@PathParam("id") Long id) {
		Evento evento = service.buscarPorId(id);
		return verificaSeEventoEhNulo(evento, id);
	}

	@GET
	@Produces("application/json")
	public List<Evento> listAll() {
		return service.buscaTodos();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public void update(@PathParam("id") long id, Evento evento) {
		verificaSeEventoEhNulo(findById(id), id);
		evento.setId(id);
		service.atualizar(evento);
	}

	private Evento verificaSeEventoEhNulo(Evento evento, long id) {
		return lanca404SeNulo(evento, "Evento com ID '" + id
				+ "' não encontrado");
	}
}