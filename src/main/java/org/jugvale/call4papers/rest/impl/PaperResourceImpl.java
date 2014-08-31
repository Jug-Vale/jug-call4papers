package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import java.util.List;

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
	public List<Paper> listarTodos() {
		return service.buscaTodos();
	}

	@Override
	public void apagaPorId(Long id) {
		Paper paper = service.buscarPorId(id);
		service.remover(lanca404SeNulo(paper, id));

	}

	@Override
	public Paper buscaPorId(Long id) {
		return service.buscarPorId(id);
	}

	@Override
	public void atualizar(long id, Paper novoPaper) {
		lanca404SeNulo(buscaPorId(id), id);
		novoPaper.setId(id);
		service.atualizar(novoPaper);
	}

}
