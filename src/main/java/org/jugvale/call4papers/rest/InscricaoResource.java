package org.jugvale.call4papers.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Participante;

@Path("incricao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface InscricaoResource {

	@POST
	@Path("evento/{eventoId}")
	public Response inscrever(@PathParam("eventoId") long eventoId, Participante participante);
	
	@POST
	@Path("{inscricaoId}/presenca")
	public Response mudaPresenca(@PathParam("inscricaoId") long inscricaoId);
}
