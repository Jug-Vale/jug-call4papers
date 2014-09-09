package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.rest.AutorResource;
import org.jugvale.call4papers.service.impl.AutorService;
import org.jugvale.call4papers.service.impl.PaperService;

public class AutorResourceImpl implements AutorResource {

	@Inject
	AutorService autorService;

	@Inject
	PaperService paperService;

	public Response criar(Autor autor) {
		autorService.salvar(autor);
		return recursoCriado(AutorResource.class, autor.getId());
	}

	@Override
	public Response apagaPorId(Long id) {
		Autor autor = lanca404SeNulo(autorService.buscarPorId(id), id);
		autorService.remover(autor);
		return Response.ok().build();
	}

	@Override
	public Response buscaPorId(Long id) {
		Autor autor = autorService.buscarPorId(id);
		return Response.ok(lanca404SeNulo(autor, id)).build();
	}

	@Override
	public Response listarTodos() {
		return Response.ok(autorService.todos()).build();
	}

	@Override
	public Response atualizar(long id, Autor novoAutor) {
		lanca404SeNulo(autorService.buscarPorId(id), id);
		novoAutor.setId(id);
		return Response.ok(autorService.atualizar(novoAutor)).build();

	}

	@Override
	public Response listaPapersPorAutor(Long autorId) {
		Autor a = autorService.buscarPorId(autorId);
		return Response.ok(
				paperService.listarPapersPorAutor(lanca404SeNulo(a, autorId)))
				.build();
	}

}