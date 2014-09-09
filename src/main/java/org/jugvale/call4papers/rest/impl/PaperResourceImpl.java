package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.rest.PaperResource;
import org.jugvale.call4papers.service.impl.PaperService;

public class PaperResourceImpl implements PaperResource {

	@Inject
	PaperService paperService;

	public Response criar(Paper paper) {
		paperService.salvar(paper);
		return recursoCriado(PaperResource.class, paper.getId());
	}

	@Override
	public Response apagaPorId(Long id) {
		Paper paper = lanca404SeNulo(paperService.buscarPorId(id), id);
		paperService.remover(paper);
		return Response.ok().build();
	}

	@Override
	public Response buscaPorId(Long id) {
		Paper paper = paperService.buscarPorId(id);
		return Response.ok(lanca404SeNulo(paper, id)).build();
	}

	@Override
	public Response listarTodos() {
		return Response.ok(paperService.todos()).build();
	}

	@Override
	public Response atualizar(long id, Paper paper)
			throws WebApplicationException {
		lanca404SeNulo(paperService.buscarPorId(id), id);
		paper.setId(id);
		return Response.ok(paperService.atualizar(paper)).build();
	}

}
