package org.jugvale.call4papers.rest;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

/**
 * 
 */
@Stateless
@Path("/eventos")
public class EventoEndpoint {
	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(Evento entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(EventoEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteById(@PathParam("id") Long id) {
		Evento evento = findById(id);
		em.remove(verificaSeEventoEhNulo(evento, id));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Evento findById(@PathParam("id") Long id) {
		Evento evento = em.find(Evento.class, id);
		return verificaSeEventoEhNulo(evento, id);
	}

	@GET
	@Produces("application/json")
	public List<Evento> listAll() {
		final List<Evento> results = em.createQuery(
				"SELECT DISTINCT e FROM Evento e ORDER BY e.id", Evento.class)
				.getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public void update(@PathParam("id") long id, Evento evento) {
		verificaSeEventoEhNulo(findById(id), id);
		evento.setId(id);
		em.merge(evento);
	}

	private Evento verificaSeEventoEhNulo(Evento evento, long id) {
		return lanca404SeNulo(evento, "Evento com ID '" + id
				+ "' não encontrado");
	}
}