package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.rest.PaperResource;
import org.jugvale.call4papers.service.impl.PaperService;

@Stateless
public class PaperResourceImpl implements PaperResource {

	@Inject
	PaperService paperService;
	
	public Response criar(Paper paper) {
		paperService.salvar(paper);
		return recursoCriado(PaperResource.class, paper.getId());
	}

	@Override
	public Response apagaPorId(Long id) {
		Paper paper = lanca404SeNulo(paperService.buscarPorId(id), id);
		paperService.remover(paper);
		return Response.ok().build();
	}

	@Override
	public Response buscaPorId(Long id) {
		Paper paper = paperService.buscarPorId(id);
		return Response.ok(lanca404SeNulo(paper, id)).build();
	}

	@Override
	public Response listarTodos() {
		return Response.ok(paperService.todos()).build();
	}

	@Override
	public Response atualizar(long id, Paper paper)
			throws WebApplicationException {
		lanca404SeNulo(paperService.buscarPorId(id), id);
		paper.setId(id);
		return Response.ok(paperService.atualizar(paper)).build();
	}

	@Override
	public Response votarNoPaper(long id) {
		Paper paper = paperService.buscarPorId(id);
		lanca404SeNulo(paper, id);
		System.out.println("Votando para " + id);
		if(paper.getEvento().getDataFim().before(new Date())) {
			long nota = paper.getNota();
			paper.setNota(nota + 1);
			paperService.atualizar(paper);
			return Response.ok().build();
		} else {
			return Response.status(Status.FORBIDDEN).entity("Evento já passou!").build();
		}
		
	}

}
