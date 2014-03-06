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

import org.jugvale.call4papers.model.Paper;
import org.jugvale.call4papers.rest.RestGererico;
import org.jugvale.call4papers.service.impl.PaperService;

/**
 * Classe boileplate para expor as operações do Paper em uma interface REST
 */
@Stateless
@Path("/papers")
public class PaperEndpoint implements RestGererico<Paper>{

	@Inject
	PaperService service;

	@Override
	@POST
	@Consumes("application/json")
	public Response create(Paper paper) {
		service.salvar(paper);
		return Response.created(
				UriBuilder.fromResource(PaperEndpoint.class)
				.path(String.valueOf(paper.getId())).build()).build();
	}

	@Override
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteById(@PathParam("id") Long id) {
		Paper paper = service.buscarPorId(id);
		service.remover(verificaSeEhNulo(paper, id));
	}

	@Override
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Paper findById(@PathParam("id") Long id) {
		Paper paper = service.buscarPorId(id);
		return verificaSeEhNulo(paper, id);
	}

	@Override
	@GET
	@Produces("application/json")
	public List<Paper> listAll() {
		return service.buscaTodos();
	}

	@Override
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public void update(@PathParam("id") long id, Paper paper) {
		verificaSeEhNulo(findById(id), id);
		paper.setId(id);
		service.atualizar(paper);
	}


	@Override
	public Paper verificaSeEhNulo(Paper entidade, long id) {
		return lanca404SeNulo(entidade, "Paper com ID '" + id + "' não encontrado");
	}
}