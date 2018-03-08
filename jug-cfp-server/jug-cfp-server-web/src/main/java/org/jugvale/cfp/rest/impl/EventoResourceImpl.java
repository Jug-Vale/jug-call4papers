package org.jugvale.cfp.rest.impl;

import static org.jugvale.cfp.rest.utils.RESTUtils.lanca404SeNulo;
import static org.jugvale.cfp.rest.utils.RESTUtils.recursoCriado;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.model.impl.Inscricao;
import org.jugvale.cfp.model.impl.Paper;
import org.jugvale.cfp.model.impl.Participante;
import org.jugvale.cfp.rest.EventoResource;
import org.jugvale.cfp.service.EventoService;
import org.jugvale.cfp.service.PaperService;
import org.jugvale.cfp.service.ParticipanteService;

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
	public Evento buscaPorId(Long id) {
		Evento evento = eventoService.buscarPorId(id);
		lanca404SeNulo(evento, id);
		return evento;
	}

	@Override
	public List<Evento> listarTodos() {
		return eventoService.todos();
	}

	@Override
	public Response atualizar(long id, Evento evento)
			throws WebApplicationException {
		lanca404SeNulo(eventoService.buscarPorId(id), id);
		evento.setId(id);
		return Response.ok(eventoService.atualizar(evento)).build();
	}

	@Override
	public List<Paper> listaPapersPorEvento(Long eventoId)
			throws WebApplicationException {
		Evento e = eventoService.buscarPorId(eventoId);
		lanca404SeNulo(e, eventoId);
		return paperService.listarPapersPorEvento(e);
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

	@Override
	public Response mudaAceitandoPapers(Long eventoId) {
		Evento e = lanca404SeNulo(eventoService.buscarPorId(eventoId), eventoId);
		e.setAceitandoTrabalhos(!e.isAceitandoTrabalhos());
		eventoService.atualizar(e);
		return Response.ok(e).build();
	}

	@Override
	public Response mudaInscricoesAbertas(Long eventoId) {
		Evento e = lanca404SeNulo(eventoService.buscarPorId(eventoId), eventoId);
		e.setInscricoesAbertas(!e.isInscricoesAbertas());
		eventoService.atualizar(e);
		return Response.ok(e).build();
	}

	@Override
	public Response baixaInscritos(Long eventoId) {
		Evento e = lanca404SeNulo(eventoService.buscarPorId(eventoId), eventoId);
		StringBuffer inscritosCSV = new StringBuffer();
		String nomeArquivo = "Inscritos_" +  e.getNome().replaceAll(" ", "") + ".csv";
		List<Inscricao> inscritosNoEvento = eventoService.inscritosNoEvento(e);
		inscritosCSV.append("nome,rg,email,empresa,instituicao,dataInscricao,compareceu\n");
		for (Inscricao inscricao : inscritosNoEvento) {
			inscritosCSV.append("\"");
			inscritosCSV.append(inscricao.getParticipante().getNome());
			inscritosCSV.append("\",\"");
			inscritosCSV.append(inscricao.getParticipante().getRg());
			inscritosCSV.append("\",\"");
			inscritosCSV.append(inscricao.getParticipante().getEmail());
			inscritosCSV.append("\",\"");
			inscritosCSV.append(inscricao.getParticipante().getEmpresa());
			inscritosCSV.append("\",\"");
			inscritosCSV.append(inscricao.getParticipante().getInstituicao());
			inscritosCSV.append("\",\"");
			inscritosCSV.append(inscricao.getData());
			inscritosCSV.append("\",\"");
			inscritosCSV.append(inscricao.isCompareceu());
			inscritosCSV.append("\"\n");
		}
		return Response.ok(inscritosCSV.toString().getBytes()).header("Content-disposition", "attachment;filename=\"" + nomeArquivo +"\"").build();
	}

}