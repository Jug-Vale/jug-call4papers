package org.jugvale.call4papers.rest.impl;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.rest.AutorResource;
import org.jugvale.call4papers.service.impl.AutorService;

public class AutorResourceImpl implements AutorResource {

	@Inject
	AutorService autores;

	public Response criar(Autor autor) {
		return autores.novo(autor);
	}

	@Override
	public Response apagaPorId(Long autor) {
		return autores.remover(autor);
	}

	@Override
	public Response buscaPorId(Long id) {
		return autores.comId(id);
	}

	@Override
	public Response listarTodos() {
		return autores.todos();
	}

	@Override
	public Response atualizar(long id, Autor novoAutor) {
		return autores.atualizar(id, novoAutor);
	}

	@Override
	public Response listaPapersPorAutor(Long autorId) {
		return autores.papersComAutor(autorId);
	}

}