package org.jugvale.cfp.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jugvale.cfp.model.Participante;

@Path("inscricao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json; charset=UTF-8")
public class InscricaoResource {

	@POST
	@Path("evento/{eventoId}")
	public Response inscrever(@PathParam("eventoId") long eventoId, Participante participante) {
		return Response.ok().build();
	}
	
	@POST
	@Path("/{inscricaoId}/presenca")
	public Response mudaPresenca(@PathParam("inscricaoId") long inscricaoId) {
		return Response.ok().build();
	}

	
	@DELETE
	@Path("/admin/{inscricaoId}")
	@RolesAllowed({ "ADMINISTRADOR" })
	@Produces(MediaType.TEXT_PLAIN)
	public Response anulaInscricao(@PathParam("inscricaoId") long id) {
		return Response.ok().build();
	}
}