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
import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.model.impl.Paper;
import org.jugvale.cfp.model.impl.Participante;

import com.fasterxml.jackson.annotation.JsonView;

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
	public Response apagaPorId(@PathParam("id") Long id);

	@GET
	@PermitAll
	@Path("/{id}")
	public Evento buscaPorId(@PathParam("id") Long id);

	@PUT
	@Path("/{id}")
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response atualizar(@PathParam("id") long id, Evento evento);

	@GET
	@Path("/{eventoId}/papers")
	@PermitAll
	@JsonView({ Views.ResumoPaper.class })
	public List<Paper> listaPapersPorEvento(@PathParam("eventoId") Long eventoId);

	/**
	 * Inscreve um participante no evento passado. No futuro deveremos pedir um
	 * token para inscrição e verificação do e-mail.
	 * 
	 * @param participante
	 *            O participante a ser inscrito. Se o mesmo nunca foi em nenhum
	 *            evento, será criado.
	 * @param eventoId
	 *            O ID do evento para o qual ele deseja se inscrever.
	 * @return
	 */
	@POST
	@Path("/{eventoId}/inscrever")
	@PermitAll
	@JsonView({ Views.Publico.class })
	public Response inscreverParticipante(Participante participante, @PathParam("eventoId") Long eventoId);

	@GET
	@Path("/{eventoId}/inscritos")
	@PermitAll
	@JsonView({ Views.Publico.class })
	public Response buscarInscritos(@PathParam("eventoId") Long eventoId);

	/**
	 * Retorna os inscritos para esse evento com todos os campos do participante
	 * <br />
	 * Acessível somente para usuários administradores do sistema!
	 * 
	 * @param eventoId
	 * @return
	 */
	@GET
	@Path("/admin/{eventoId}/inscritos")
	@RolesAllowed({ "ADMINISTRADOR" })
	@JsonView({ Views.Interno.class })
	public Response buscarInscritosTodosCampos(@PathParam("eventoId") Long eventoId);

	@POST
	@Path("/admin/{eventoId}/muda-aceitando-papers")
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response mudaAceitandoPapers(@PathParam("eventoId") Long eventoId);

	@POST
	@Path("/admin/{eventoId}/muda-inscricoes-abertas")
	@RolesAllowed({ "ADMINISTRADOR" })
	public Response mudaInscricoesAbertas(@PathParam("eventoId") Long eventoId);

	@GET
	@Path("/admin/{eventoId}/inscritos/baixar")
	@RolesAllowed({ "ADMINISTRADOR" })
	@Produces("text/csv;charset=utf-8")
	public Response baixaInscritos(@PathParam("eventoId") Long eventoId);

}
