package org.jugvale.call4papers.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.rest.captcha.VerificaCaptcha;

@Path("paper")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PaperResource {

	@POST
	@PermitAll
	@VerificaCaptcha
	public Response criar(Paper paper);

	@GET
	@PermitAll
	public Response listarTodos();

	@DELETE
	@Path("/{id}")
	@RolesAllowed({"ADMINISTRADOR"})
	public Response apagaPorId(@PathParam("id") Long id);

	@GET
	@Path("/{id}")
	@PermitAll
	public Response buscaPorId(@PathParam("id") Long id);

	@PUT
	@Path("/{id}")
	@RolesAllowed({"ADMINISTRADOR"})
	public Response atualizar(@PathParam("id") long id, Paper paper);
	
	@POST
	@Path("/{id}/votar")
	@VerificaCaptcha
	public Response votarNoPaper(@PathParam("id") long id);

}
