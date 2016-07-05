package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Inscricao;
import org.jugvale.call4papers.model.impl.Participante;
import org.jugvale.call4papers.rest.InscricaoResource;
import org.jugvale.call4papers.service.impl.EventoService;
import org.jugvale.call4papers.service.impl.InscricaoService;
import org.jugvale.call4papers.service.impl.ParticipanteService;

@Stateless
public class InscricaoResourceImpl implements InscricaoResource {

	@Inject
	EventoService eventoService;

	@Inject
	ParticipanteService participanteService;
	
	@Inject
	InscricaoService inscricaoService;
	
	@Inject EmailService emailService;

	@Override
	public Response inscrever(long eventoId, Participante participante) {
		ResponseBuilder rb;
		Evento evento = lanca404SeNulo(eventoService.buscarPorId(eventoId),
				eventoId);
		Participante partipanteExistente = participanteService
				.buscaPorEmail(participante.getEmail());
		if (!evento.isInscricoesAbertas()) {
			return Response.status(Status.FORBIDDEN)
					.entity("Inscrições já passaram").build();
		}
		if (partipanteExistente != null) {
			participante.setId(partipanteExistente.getId());
			participante = participanteService.atualizar(participante);
		} else {
			participanteService.salvar(participante);		
		
		}
		Inscricao inscricao = eventoService
				.buscaInscricao(evento, participante);
		if (inscricao != null) {
			rb = Response.status(Status.CONFLICT);
		} else {
			inscricao = eventoService.inscreverParticipante(evento,
					participante);
			rb = Response.ok();
			emailService.enviarEMail(evento, participante);		
			
		}
		return rb.entity(inscricao).build();
	}
	
	@Override
	public Response mudaPresenca(long inscricaoId) {
		Inscricao inscricao = inscricaoService.buscarPorId(inscricaoId);
		inscricao.setCompareceu(!inscricao.isCompareceu());
		return Response.ok(inscricao).build();
	}

	

}
