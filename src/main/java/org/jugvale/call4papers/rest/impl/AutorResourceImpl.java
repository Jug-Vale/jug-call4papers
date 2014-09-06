package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
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
		return recursoCriado( AutorResource.class, autor.getId() );
	}

	@Override
	public Response apagaPorId(Long id) {
		
		try {
			Autor autor = lanca404SeNulo(autorService.buscarPorId(id), id);
			autorService.remover(autor);
			return Response.ok().build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response buscaPorId(Long id) {
		
		try {
			Autor autor = autorService.buscarPorId(id);
			return Response.ok( lanca404SeNulo(autor, id) ).build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response listarTodos() {
		return Response.ok( autorService.buscaTodos() ).build();
	}

	@Override
	public Response atualizar(long id, Autor novoAutor) {
		
		try {
			lanca404SeNulo(buscaPorId(id), id);
			novoAutor.setId(id);
			autorService.atualizar(novoAutor);
			return Response.ok().build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response listaPapersPorAutor(Long autorId) {
		
		try {
			Autor autor = autorService.buscarPorId(autorId);
			lanca404SeNulo(autor, autorId);
			return Response.ok( paperService.listarPapersPorAutor(autor) ).build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

}