package org.jugvale.call4papers.rest;

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
import org.jugvale.call4papers.service.impl.PaperService;

/**
 * Classe boileplate para expor as operações do Paper em uma interface REST
 */
@Stateless
@Path("/papers")
public class PaperEndpoint {

	@Inject
	PaperService service;

	@POST
	@Consumes("application/json")
	public Response create(Paper paper) {
		service.salvar(paper);
		return Response.created(
				UriBuilder.fromResource(PaperEndpoint.class)
						.path(String.valueOf(paper.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteById(@PathParam("id") Long id) {
		Paper paper = service.buscarPorId(id);
		service.remover(verificaSePaperEhNulo(paper, id));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Paper findById(@PathParam("id") Long id) {
		Paper paper = service.buscarPorId(id);
		return verificaSePaperEhNulo(paper, id);
	}

	@GET
	@Produces("application/json")
	public List<Paper> listAll() {
		return service.buscaTodos();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public void update(@PathParam("id") long id, Paper paper) {
		verificaSePaperEhNulo(findById(id), id);
		paper.setId(id);
		service.atualizar(paper);		
	}

	private Paper verificaSePaperEhNulo(Paper paper, long id) {
		return lanca404SeNulo(paper, "Paper com ID '" + id + "' não encontrado");
	}
}