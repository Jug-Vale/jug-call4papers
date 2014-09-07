package org.jugvale.call4papers.service.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.repositorio.impl.Papers;
import org.jugvale.call4papers.rest.PaperResource;
import org.jugvale.call4papers.service.Service;

public class PaperService implements Service<Paper>{

	@Inject
	Papers papers;
	
	@Override
	public Response novo(Paper paper) {
		papers.novo(paper);
		return recursoCriado(PaperResource.class, paper.getId());
	}

	@Override
	public Response remover(Long id) throws WebApplicationException {
		try {
			Paper paper = papers.comId(id);
			papers.remover( lanca404SeNulo(paper, id) );
			return Response.ok().build();

		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response comId(Long id) throws WebApplicationException {
		try {
			Paper paper = papers.comId(id);
			lanca404SeNulo(paper, id);
			return Response.ok( paper ).build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

	@Override
	public Response todos() {
		return Response.ok( papers.todos() ).build();
	}

	@Override
	public Response atualizar(Long id, Paper novoPaper)
			throws WebApplicationException {
		
		try {
			lanca404SeNulo(papers.comId(id), id);
			novoPaper.setId(id);
			papers.atualizar(novoPaper);
			return Response.ok().build();
			
		} catch (WebApplicationException e) {
			return e.getResponse();
		}
	}

}
