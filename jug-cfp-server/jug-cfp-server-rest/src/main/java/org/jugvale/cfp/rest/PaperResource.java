package org.jugvale.cfp.rest;

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

import org.jugvale.cfp.model.config.Views;
import org.jugvale.cfp.model.impl.Paper;

import com.fasterxml.jackson.annotation.JsonView;

@Path("paper")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json; charset=UTF-8")
public interface PaperResource {

	@POST
	@PermitAll
	public Response criar(Paper paper);

	@GET
	@PermitAll
	@JsonView({Views.Publico.class})
	public List<Paper> listarTodos();

	@DELETE
	@Path("/{id}")
	@RolesAllowed({"ADMINISTRADOR"})
	public Response apagaPorId(@PathParam("id") Long id);

	@GET
	@Path("/{id}")
	@PermitAll
	@JsonView(Views.Publico.class)
	public Response buscaPorId(@PathParam("id") Long id);

	@PUT
	@Path("/{id}")
	@RolesAllowed({"ADMINISTRADOR"})
	public Response atualizar(@PathParam("id") long id, Paper paper);
	
	@POST
	@PermitAll
	@Path("/{id}/votar")
	public Response votarNoPaper(@PathParam("id") long id);

}
