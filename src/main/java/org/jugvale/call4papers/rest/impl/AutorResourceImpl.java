package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.rest.AutorResource;
import org.jugvale.call4papers.service.impl.AutorService;
import org.jugvale.call4papers.service.impl.PaperService;

public class AutorResourceImpl implements AutorResource {

	@Inject
	AutorService service;

	@Inject
	PaperService paperService;

	public Response criar(Autor autor) {
		service.salvar(autor);
		return recursoCriado(AutorResource.class, autor.getId());
	}

	@Override
	public void apagaPorId(Long id) {
		Autor autor = service.buscarPorId(id);
		service.remover(lanca404SeNulo(autor, id));
	}

	@Override
	public Autor buscaPorId(Long id) {
		Autor autor = service.buscarPorId(id);
		return lanca404SeNulo(autor, id);
	}

	@Override
	public List<Autor> listarTodos() {
		return service.buscaTodos();
	}

	@Override
	public void atualizar(long id, Autor novoAutor) {
		lanca404SeNulo(buscaPorId(id), id);
		novoAutor.setId(id);
		service.atualizar(novoAutor);
	}

	@Override
	public List<Paper> listaPapersPorAutor(Long autorId) {
		Autor autor = buscaPorId(autorId);
		return paperService.listarPapersPorAutor(autor);
	}

}