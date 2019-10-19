package org.jugvale.cfp.rest;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jugvale.cfp.model.Inscricao;

@Path("inscricao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json; charset=UTF-8")
public class InscricaoResource {

	@POST
	@Transactional
	@Path("{inscricaoId}/presenca")
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response mudaPresenca(@PathParam("inscricaoId") long inscricaoId) {
	    Inscricao inscricao = Inscricao.findById(inscricaoId);
	    return RESTUtils.checkEntityAndUpdate(inscricao, i -> i.compareceu = !i.compareceu);
	}
	
    @GET
    @Path("{inscricaoId}")
    public Response busca(@PathParam("inscricaoId") long inscricaoId) {
        Inscricao inscricao = Inscricao.findById(inscricaoId);
        return RESTUtils.responseForNullableEntity(inscricao);
    }
	
	@DELETE
	@Transactional
	@Path("{inscricaoId}")
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response anulaInscricao(@PathParam("inscricaoId") long id) {
	    Inscricao.delete("id", id);
		return Response.noContent().build();
	}
}
