package org.jugvale.call4papers.rest.impl;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.rest.PaperResource;
import org.jugvale.call4papers.service.impl.PaperService;

public class PaperResourceImpl implements PaperResource {

	@Inject
	PaperService papers;

	@Override
	public Response criar(Paper paper) {
		return papers.novo(paper);
	}

	@Override
	public Response listarTodos() {
		return papers.todos();
	}

	@Override
	public Response apagaPorId(Long id) {
		return papers.remover(id);
	}

	@Override
	public Response buscaPorId(Long id) {
		return papers.comId(id);
	}

	@Override
	public Response atualizar(long id, Paper paper) {
		return papers.atualizar(id, paper);
	}

}
