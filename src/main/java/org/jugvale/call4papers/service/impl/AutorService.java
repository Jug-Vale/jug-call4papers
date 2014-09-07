package org.jugvale.call4papers.service.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.repositorio.impl.Autores;
import org.jugvale.call4papers.repositorio.impl.Papers;
import org.jugvale.call4papers.rest.AutorResource;
import org.jugvale.call4papers.service.Service;

public class AutorService implements Service<Autor> {
	
	@Inject
	Autores autores;

	@Inject
	Papers papers;
	
	@Override
	public Response novo(Autor autor) {
		autores.novo(autor);
		return recursoCriado( AutorResource.class, autor.getId() );
	}

	@Override
	public Response remover(Long autorId) throws WebApplicationException {
		try {
			Autor autor = lanca404SeNulo( autores.comId(autorId), autorId );
			autores.remover(autor);
			return Response.ok().build();
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response comId(Long id) throws WebApplicationException {
		try {
			Autor autor = autores.comId(id);
			return Response.ok( lanca404SeNulo(autor, id) ).build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response todos() {
		return Response.ok( autores.todos() ).build();
	}

	@Override
	public Response atualizar(Long id, Autor novoAutor) throws WebApplicationException {
		
		try {
			lanca404SeNulo(autores.comId(id), id);
			novoAutor.setId(id);
			autores.atualizar(novoAutor);
			return Response.ok().build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	public Response papersComAutor(Long id) throws WebApplicationException {
		try {
			Autor autor = autores.comId(id);
			lanca404SeNulo(autor, id);
			return Response.ok( papers.papersDoAutor(autor) ).build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}

	}

}
