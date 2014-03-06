package org.jugvale.call4papers.rest.impl;

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

import org.jugvale.call4papers.model.Autor;

/**
 * 
 */
@Stateless
@Path("/autores")
public class AutorEndpoint {
	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(Autor entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(AutorEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteById(@PathParam("id") Long id) {
		Autor autor = em.find(Autor.class, id);
		em.remove(verificaSeAutorEhNulo(autor, id));		
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Autor findById(@PathParam("id") Long id) {
		Autor autor = em.find(Autor.class, id);
		return verificaSeAutorEhNulo(autor, id);
	}

	@GET
	@Produces("application/json")
	public List<Autor> listAll() {
		final List<Autor> results = em
				.createQuery(
						"SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.papers ORDER BY a.id",
						Autor.class).getResultList();
		return results;
	}	

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public void update(@PathParam("id") long id, Autor novoAutor) {
		verificaSeAutorEhNulo(findById(id), id);
		novoAutor.setId(id);
		em.merge(novoAutor);
	}

	/**
	 * 
	 * Lança exceção de 404 se o Autor for nulo.
	 * @param autor
	 * @param id
	 * @return
	 */
	private Autor verificaSeAutorEhNulo(Autor autor, long id) {
		return lanca404SeNulo(autor, "Autor com ID '" + id + "' não encontrado");
	}
}
