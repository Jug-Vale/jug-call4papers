package org.jugvale.cfp.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jugvale.cfp.model.Paper;
import org.jugvale.cfp.model.dto.ResumoPaper;

@Path("paper")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json; charset=UTF-8")
public class PaperResource {

	@POST
	@PermitAll
	@Transactional
	public Response criar(Paper paper) {
	    paper.persist();
		return RESTUtils.created(PaperResource.class, paper.id);
	}

	@DELETE
	@Path("/{id}")
	@Transactional
	@RolesAllowed({"ADMINISTRADOR"})
	public Response apagaPorId(@PathParam("id") Long id) {
	    Paper.delete("id", id);
		return Response.noContent().build();
	}

	@GET
	@PermitAll
	@Path("/{id}")
	public Response buscaPorId(@PathParam("id") Long id) {
	    Paper paper = Paper.findById(id);
		return RESTUtils.checkNullableEntityAndRemap(paper, ResumoPaper::of);
	}

	@PUT
	@Path("/{id}")
	@Transactional
	@RolesAllowed({"ADMINISTRADOR"})
	public Response atualizar(@PathParam("id") long id, Paper newPaper) {
	    Paper paper = Paper.findById(id);
	    return RESTUtils.checkEntityAndUpdate(paper, p -> {
	        p.aceito = newPaper.aceito;
	        p.dataSubmissao = newPaper.dataSubmissao;
	        p.descricao = newPaper.descricao;
	        p.tipo = newPaper.tipo;
	        p.titulo = newPaper.titulo;
	        p.persist();
	    });
	}
	
	@POST
	@PermitAll
	@Transactional
	@Path("/{id}/votar")
	public Response votarNoPaper(@PathParam("id") long id) {
	    Paper paper = Paper.findById(id);
	    return RESTUtils.checkEntityAndUpdate(paper, p -> p.nota = p.nota + 1);
	}

}
