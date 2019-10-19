package org.jugvale.cfp.rest;

import static org.jugvale.cfp.rest.RESTUtils.checkEntityAndUpdate;
import static org.jugvale.cfp.rest.RESTUtils.checkNullableEntityAndReturn;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.event.Event;
import javax.inject.Inject;
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
import javax.ws.rs.core.Response.Status;

import org.jugvale.cfp.definitions.Roles;
import org.jugvale.cfp.events.EventoNovaInscricao;
import org.jugvale.cfp.model.Evento;
import org.jugvale.cfp.model.Inscricao;
import org.jugvale.cfp.model.Paper;
import org.jugvale.cfp.model.Participante;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Path("evento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json; charset=UTF-8")
public class EventoResource {
    
    @Inject
    Event<EventoNovaInscricao> eventoNovaInscricao;

	@POST
	@Transactional
	@RolesAllowed({ Roles.ADMINISTRADOR })
	public Response criar(Evento evento) {
		evento.persist();
		return RESTUtils.created(EventoResource.class, evento.id);
	}

	@GET
	@PermitAll
	public Response listarTodos(@QueryParam("size") @DefaultValue("100") int size,
									@QueryParam("page") @DefaultValue("0") int page) {
		return Response.ok(Evento.findAll().page(page, size).list()).build();
	}

	@DELETE
	@Path("/{id}")
	@Transactional
	@RolesAllowed({ Roles.ADMINISTRADOR })
	public Response apagaPorId(@PathParam("id") Long id) {
	    Evento.delete("id", id);
		return Response.noContent().build();
	}

	@GET
	@PermitAll
	@Path("/{id}")
	public Response buscaPorId(@PathParam("id") Long id) {
		Evento evento = Evento.findById(id);
		return RESTUtils.responseForNullableEntity(evento);
	}

	@PUT
	@Path("/{id}")
	@Transactional
	@RolesAllowed({ Roles.ADMINISTRADOR })
	public Response atualizar(@PathParam("id") long id, Evento evento) {
		Evento eventoExistente = Evento.findById(id);
		return RESTUtils.checkEntityAndUpdate(eventoExistente, e -> {
		      e.descricao = evento.descricao;
		      e.dataInicio = evento.dataInicio;
		      e.dataFim = evento.dataFim;
		      e.local = evento.local;
		      e.url = evento.url;
		      e.aceitandoTrabalhos = evento.aceitandoTrabalhos;
		      e.inscricoesAbertas = evento.inscricoesAbertas;
		});
	}

	@GET
	@PermitAll
	@Path("/{eventoId}/papers")
	public Response listaPapersPorEvento(@PathParam("eventoId") Long id) {
	    Evento evento = Evento.findById(id);
	    return checkNullableEntityAndReturn(evento, Paper::porEvento);
	}

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
	@PermitAll
	@Transactional
	@Path("/{eventoId}/inscrever")
	public Response inscreverParticipante(Participante participante, @PathParam("eventoId") Long id) {
	    Evento evento = Evento.findById(id);
	    Participante p1 = Participante.merge(participante);
	    PanacheEntityBase i = Inscricao.find("evento = ?1 and participante = ?2", evento, p1).firstResult();
	    if (i != null) {
	        return Response.status(Status.CONFLICT).entity("Participante com email já inscrito!").build();
	    } else {
	        return RESTUtils.checkNullableEntitiesAndRemap(evento, p1, (e, p) -> {
	            Inscricao inscricao = Inscricao.of(p, e); 
	            inscricao.persist();
	            eventoNovaInscricao.fire(new EventoNovaInscricao(inscricao));
	            return inscricao;
	        });
	    }
	}

	@GET
	@PermitAll
	@Path("/{eventoId}/inscritos")
	public Response buscarInscritos(@PathParam("eventoId") Long id) {
	    Evento evento = Evento.findById(id);
	    return checkNullableEntityAndReturn(evento, Inscricao::porEvento);
	}

	/**
	 * Retorna os inscritos para esse evento com todos os campos do participante
	 * <br />
	 * Acessível somente para usuários administradores do sistema!
	 * 
	 * @param eventoId
	 * @return
	 */
	@GET
	@RolesAllowed({ Roles.ADMINISTRADOR })
	@Path("/admin/{eventoId}/inscritos")
	public Response buscarInscritosTodosCampos(@PathParam("eventoId") Long id) {
	    Evento evento = Evento.findById(id);
	    return checkNullableEntityAndReturn(evento, e -> Inscricao.find("evento", e).list());
	}

	@POST
	@Transactional
	@RolesAllowed({ Roles.ADMINISTRADOR })
	@Path("/admin/{eventoId}/muda-aceitando-papers")
	public Response mudaAceitandoPapers(@PathParam("eventoId") Long id) {
	    Evento evento = Evento.findById(id);
	    return checkEntityAndUpdate(evento, e -> e.aceitandoTrabalhos = !e.aceitandoTrabalhos);
	}

	@POST
	@Transactional
	@Path("/admin/{eventoId}/muda-inscricoes-abertas")
	@RolesAllowed({ Roles.ADMINISTRADOR })
	public Response mudaInscricoesAbertas(@PathParam("eventoId") Long id) {
	    Evento evento = Evento.findById(id);
	    return checkEntityAndUpdate(evento, e -> e.inscricoesAbertas = !e.inscricoesAbertas);
	}

}