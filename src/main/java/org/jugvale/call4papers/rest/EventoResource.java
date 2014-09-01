package org.jugvale.call4papers.rest;

import java.util.List;

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

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;

@Path("evento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface EventoResource {

	@POST
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response criar(Evento Evento);

	@GET
	@PermitAll
	public List<Evento> listarTodos();

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ "ADMINISTRADOR" })
	public void apagaPorId(@PathParam("id") Long id);

	@GET
	@Path("/{id}")
	@PermitAll
	public Evento buscaPorId(@PathParam("id") Long id);

	@PUT
	@Path("/{id}")
	@RolesAllowed({"ADMINISTRADOR"})
	public void atualizar(@PathParam("id") long id, Evento evento);

	@GET
	@Path("/{eventoId}/papers")
	@PermitAll
	public List<Paper> listaPapersPorEvento(@PathParam("eventoId") Long eventoId);

}
