package org.jugvale.call4papers.rest;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jugvale.call4papers.model.impl.Participante;

@Path("participante")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ParticipanteResource {

	/**
	 * Atualização de dados do participante SEM login e senha.
	 * 
	 * @param token
	 *            Um token de atualização enviado ao e-mail dele.
	 * @param participante
	 *            Os dados novos para atualização.
	 * @return
	 */
	@PUT
	@PermitAll
	public Response criar(@QueryParam("token") String token,
			Participante participante);

}
