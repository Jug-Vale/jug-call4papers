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

import org.jugvale.call4papers.model.Paper;

/**
 * 
 */
@Stateless
@Path("/papers")
public class PaperEndpoint {
	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(Paper entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(PaperEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteById(@PathParam("id") Long id) {
		Paper paper = findById(id);
		em.remove(verificaSePaperEhNulo(paper, id));		
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Paper findById(@PathParam("id") Long id) {
		Paper paper = em.find(Paper.class, id);
		return verificaSePaperEhNulo(paper, id);
	}

	@GET
	@Produces("application/json")
	public List<Paper> listAll() {
		final List<Paper> results = em
				.createQuery(
						"SELECT DISTINCT p FROM Paper p LEFT JOIN FETCH p.autores LEFT JOIN FETCH p.evento ORDER BY p.id",
						Paper.class).getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public void update(@PathParam("id") long id, Paper paper) {
		paper = em.merge(paper);
		verificaSePaperEhNulo(paper, id);
	}

	private Paper verificaSePaperEhNulo(Paper paper, long id) {
		return lanca404SeNulo(paper, "Paper com ID '" + id + "' não encontrado");
	}
}