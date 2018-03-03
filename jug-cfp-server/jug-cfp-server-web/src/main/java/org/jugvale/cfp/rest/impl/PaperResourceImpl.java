package org.jugvale.cfp.rest.impl;

import static org.jugvale.cfp.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.cfp.rest.utils.RESTUtils.recursoCriado;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jugvale.cfp.model.impl.Paper;
import org.jugvale.cfp.rest.PaperResource;
import org.jugvale.cfp.rest.voto.VotosSalvos;
import org.jugvale.cfp.service.PaperService;

@Stateless
public class PaperResourceImpl implements PaperResource {

	@Inject
	PaperService paperService;

	@Inject
	VotosSalvos votosSalvos;
	
	@Context HttpServletRequest request;

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
	public List<Paper> listarTodos() {
		return paperService.todos();
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
		ResponseBuilder rb;
		Paper paper = paperService.buscarPorId(id);
		lanca404SeNulo(paper, id);
		String ip = request.getRemoteAddr();
		if (!paper.getEvento().getDataFim().after(new Date())) {
			rb = Response.status(Status.FORBIDDEN).entity(
					"Esse evento já passou.");
		} else if (votosSalvos.ipJaVotouNoPaper(ip, id)) {
			System.out.printf("Proibindo %s de votar no paper %d.\n", ip, id);
			rb = Response.status(Status.FORBIDDEN).entity(
					"Você já votou nesse Paper!");
		} else {
			System.out.printf("IP %s votando para paper %d\n", ip, id);
			long nota = paper.getNota();
			paper.setNota(nota + 1);
			paperService.atualizar(paper);
			rb = Response.ok();
		}
		return rb.build();
	}

}
