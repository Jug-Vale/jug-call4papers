package org.jugvale.call4papers.rest;

import java.util.List;

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

import org.jugvale.call4papers.model.impl.Autor;

@Path("autor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AutorResource {

	@POST
	public Response criar(Autor autor);

	@GET
	public List<Autor> listarTodos();

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void apagaPorId(@PathParam("id") Long id);

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Autor buscaPorId(@PathParam("id") Long id);

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public void atualizar(@PathParam("id") long id, Autor entidade);

}