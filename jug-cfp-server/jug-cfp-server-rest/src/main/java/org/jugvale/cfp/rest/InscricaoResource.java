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

import org.jugvale.cfp.model.impl.Participante;

@Path("inscricao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface InscricaoResource {

	@POST
	@Path("evento/{eventoId}")
	public Response inscrever(@PathParam("eventoId") long eventoId, Participante participante);
	
	@POST
	@Path("/admin/{inscricaoId}/presenca")
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response mudaPresenca(@PathParam("inscricaoId") long inscricaoId);

	
	@DELETE
	@Path("/admin/{inscricaoId}")
	@RolesAllowed({ "ADMINISTRADOR" })
	@Produces(MediaType.TEXT_PLAIN)
	public Response anulaInscricao(@PathParam("inscricaoId") long id);
}
