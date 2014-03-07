package org.jugvale.call4papers.rest;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public abstract class RestAbstrato<T> {

	@POST
	@Consumes("application/json")
	public abstract Response criar(T entidade);

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public abstract void apagaPorId(@PathParam("id") Long id);

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public abstract T buscaPorId(@PathParam("id") Long id);

	@GET
	@Produces("application/json")
	public abstract List<T> listarTodos();

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public abstract void atualizar(@PathParam("id") long id, T entidade);

	protected T verificaSeEhNulo(T entidade, long id) {
		return lanca404SeNulo(entidade, "ID: '" + id + "' não encontrado");
	}

}
