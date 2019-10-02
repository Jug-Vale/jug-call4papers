package org.jugvale.cfp.rest;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jugvale.cfp.model.Autor;
import org.jugvale.cfp.model.Paper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Path("autor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json; charset=UTF-8")
public class AutorResource {

	@POST
	@PermitAll
	@Transactional
	public Response criar(Autor autor) {
		autor.persist();
		return 	RESTUtils.created(AutorResource.class, autor.id);
	}

	@GET
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response listarTodos(@QueryParam("size") @DefaultValue("100") int size,
															@QueryParam("page") @DefaultValue("0") int page) {
		List<PanacheEntityBase> autores = Autor.findAll().page(page, size).list();
		return Response.ok(autores).build();

	}

	@DELETE
	@Path("/{id}")
	@Transactional
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response apagaPorId(@PathParam("id") Long id) {
		Autor.delete("id", id);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id}")
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response buscaPorId(@PathParam("id") Long id) {
		Autor autor = Autor.findById(id);
		return RESTUtils.responseForNullableEntity(autor);
	}
	@GET
	@PermitAll
	@Path("/{autorId}/papers")
	public Response listaPapersPorAutor(@PathParam("autorId") Long id) {
		Autor autor = Autor.findById(id);
		return RESTUtils.checkNullableEntityAndReturn(autor, a -> Paper.find("autor", a).list());
	}

	@PUT
	@Path("/{id}")
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response atualizar(@PathParam("id") long id, Autor entidade) {
		return Response.ok().build();
	}

}
