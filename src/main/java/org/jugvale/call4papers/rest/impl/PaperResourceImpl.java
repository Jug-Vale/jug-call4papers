package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.rest.PaperResource;
import org.jugvale.call4papers.service.impl.PaperService;

public class PaperResourceImpl implements PaperResource {

	@Inject
	PaperService service;

	@Override
	public Response criar(Paper paper) {
		service.salvar(paper);
		return recursoCriado(PaperResource.class, paper.getId());
	}

	@Override
	public Response listarTodos() {
		return Response.ok(service.buscaTodos()).build();
	}

	@Override
	public Response apagaPorId(Long id) {
		Paper paper = service.buscarPorId(id);
		service.remover(lanca404SeNulo(paper, id));
		return Response.ok().build();
	}

	@Override
	public Response buscaPorId(Long id) {
		return Response.ok(service.buscarPorId(id)).build();
	}

	@Override
	public Response atualizar(long id, Paper novoPaper) {
		lanca404SeNulo(buscaPorId(id), id);
		novoPaper.setId(id);
		service.atualizar(novoPaper);
		return Response.ok().build();
	}

}
