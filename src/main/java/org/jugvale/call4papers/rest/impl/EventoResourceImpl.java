package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.call4papers.rest.utils.RESTUtils.recursoCriado;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Inscricao;
import org.jugvale.call4papers.model.impl.Participante;
import org.jugvale.call4papers.rest.EventoResource;
import org.jugvale.call4papers.service.impl.EventoService;
import org.jugvale.call4papers.service.impl.PaperService;
import org.jugvale.call4papers.service.impl.ParticipanteService;

@Stateless
public class EventoResourceImpl implements EventoResource {

	@Inject
	EventoService eventoService;

	@Inject
	PaperService paperService;

	@Inject
	ParticipanteService participanteService;

	public Response criar(Evento evento) {
		eventoService.salvar(evento);
		return recursoCriado(EventoResource.class, evento.getId());
	}

	@Override
	public Response apagaPorId(Long id) {
		Evento evento = lanca404SeNulo(eventoService.buscarPorId(id), id);
		eventoService.remover(evento);
		return Response.ok().build();
	}

	@Override
	public Response buscaPorId(Long id) {
		Evento evento = eventoService.buscarPorId(id);
		return Response.ok(lanca404SeNulo(evento, id)).build();
	}

	@Override
	public Response listarTodos() {
		return Response.ok(eventoService.todos()).build();
	}

	@Override
	public Response atualizar(long id, Evento evento)
			throws WebApplicationException {
		lanca404SeNulo(eventoService.buscarPorId(id), id);
		evento.setId(id);
		return Response.ok(eventoService.atualizar(evento)).build();
	}

	@Override
	public Response listaPapersPorEvento(Long eventoId)
			throws WebApplicationException {
		Evento e = eventoService.buscarPorId(eventoId);
		lanca404SeNulo(e, eventoId);
		return Response.ok(paperService.listarPapersPorEvento(e)).build();
	}

	@Override
	public Response inscreverParticipante(Participante participante,
			Long eventoId) {
		ResponseBuilder rb;
		Evento evento = lanca404SeNulo(eventoService.buscarPorId(eventoId),
				eventoId);
		Participante partipanteExistente = participanteService
				.buscaPorEmail(participante.getEmail());
		if (partipanteExistente != null) {
			participante.setId(partipanteExistente.getId());
		} else {
			participanteService.salvar(participante);
		}
		Inscricao inscricao = eventoService
				.buscaInscricao(evento, participante);
		if (inscricao != null) {
			rb = Response.notModified();
		} else {
			inscricao = eventoService.inscreverParticipante(evento,
					participante);
			rb = Response.ok();
			System.out.println("Participante com e-mail "
					+ participante.getEmail() + " inscrito.");
		}
		return rb.entity(inscricao).build();
	}

	@Override
	public Response buscarInscritos(Long eventoId) {
		Evento e = lanca404SeNulo(eventoService.buscarPorId(eventoId), eventoId);
		return Response.ok(eventoService.inscritosNoEvento(e)).build();
	}

	@Override
	public Response buscarInscritosTodosCampos(Long eventoId) {
		return buscarInscritos(eventoId);
	}

}