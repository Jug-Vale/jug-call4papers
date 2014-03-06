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

import org.jugvale.call4papers.model.Autor;
import org.jugvale.call4papers.rest.RestGererico;
import org.jugvale.call4papers.service.impl.AutorService;

/**
 * 
 */
@Stateless
@Path("/autores")
public class AutorEndpoint implements RestGererico<Autor>{

	@Inject
	AutorService service;

	@Override
	@POST
	@Consumes("application/json")
	public Response create(Autor entidade) {
		service.salvar(entidade);
		return Response.created(
				UriBuilder.fromResource(AutorEndpoint.class)
				.path(String.valueOf(entidade.getId())).build()).build();
	}

	@Override
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteById(@PathParam("id") Long id) {
		Autor autor = service.buscarPorId(id);
		service.remover(verificaSeEhNulo(autor, id));
	}

	@Override
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Autor findById(@PathParam("id") Long id) {
		Autor autor = service.buscarPorId(id);
		return verificaSeEhNulo(autor, id);
	}

	@Override
	@GET
	@Produces("application/json")
	public List<Autor> listAll() {
		return service.buscaTodos();
	}

	@Override
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public void update(@PathParam("id") long id, Autor novoAutor) {
		verificaSeEhNulo(findById(id), id);
		novoAutor.setId(id);
		service.atualizar(novoAutor);
	}

	/**
	 * 
	 * Lança exceção de 404 se o Autor for nulo.
	 * @param autor
	 * @param id
	 * @return
	 */

	@Override
	public Autor verificaSeEhNulo(Autor entidade, long id) {
		return lanca404SeNulo(entidade, "Autor com ID '" + id + "' não encontrado");
	}
}
