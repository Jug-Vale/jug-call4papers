package org.jugvale.cfp.rest;

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

import org.jugvale.cfp.model.config.Views;
import org.jugvale.cfp.model.impl.Autor;

import com.fasterxml.jackson.annotation.JsonView;

@Path("autor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json; charset=UTF-8")
public interface AutorResource {

	@POST
	@PermitAll
	public Response criar(Autor autor);

	@GET
	@PermitAll
	@RolesAllowed({ "ADMINISTRADOR" })
	@JsonView(Views.Interno.class)
	public Response listarTodos();

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response apagaPorId(@PathParam("id") Long id);

	@GET
	@Path("/{id}")
	@RolesAllowed({ "ADMINISTRADOR" })
	@JsonView(Views.Interno.class)
	public Response buscaPorId(@PathParam("id") Long id);

	@GET
	@Path("/{autorId}/papers")
	@PermitAll
	public Response listaPapersPorAutor(@PathParam("autorId") Long autorId);

	@PUT
	@Path("/{id}")
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response atualizar(@PathParam("id") long id, Autor entidade);

}
