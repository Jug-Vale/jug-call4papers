package org.jugvale.call4papers.rest.impl;

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
import org.jugvale.call4papers.rest.RestAbstrato;
import org.jugvale.call4papers.service.impl.EventoService;

@Stateless
@Path("/eventos")
public class EventoEndpoint extends RestAbstrato<Evento>{

	@Inject
	EventoService service;

	@Override
	@POST
	@Consumes("application/json")
	public Response create(Evento entidade) {
		service.salvar(entidade);
		return Response.created(
				UriBuilder.fromResource(EventoEndpoint.class)
				.path(String.valueOf(entidade.getId())).build())
				.build();
	}

	@Override
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteById(@PathParam("id") Long id) {
		Evento evento = findById(id);
		service.remover(verificaSeEhNulo(evento, id));
	}

	@Override
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Evento findById(@PathParam("id") Long id) {
		Evento evento = service.buscarPorId(id);
		return verificaSeEhNulo(evento, id);
	}

	@Override
	@GET
	@Produces("application/json")
	public List<Evento> listAll() {
		return service.buscaTodos();
	}

	@Override
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public void update(@PathParam("id") long id, Evento evento) {
		verificaSeEhNulo(findById(id), id);
		evento.setId(id);
		service.atualizar(evento);
	}

}