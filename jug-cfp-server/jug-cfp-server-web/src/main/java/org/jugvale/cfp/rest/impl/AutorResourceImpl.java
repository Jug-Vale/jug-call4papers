package org.jugvale.cfp.rest.impl;

import static org.jugvale.cfp.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.cfp.rest.utils.RESTUtils.recursoCriado;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jugvale.cfp.model.impl.Autor;
import org.jugvale.cfp.rest.AutorResource;
import org.jugvale.cfp.service.impl.AutorService;
import org.jugvale.cfp.service.impl.PaperService;

@Stateless
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