package org.jugvale.call4papers.rest.impl;

import static org.jugvale.call4papers.rest.utils.RESTUtils.lanca404SeNulo;

import java.text.MessageFormat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jugvale.call4papers.model.config.RemetentePadraoDeEMailConfig;
import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Inscricao;
import org.jugvale.call4papers.model.impl.Participante;
import org.jugvale.call4papers.rest.InscricaoResource;
import org.jugvale.call4papers.sender.EmailSender;
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
	
	@Inject
	EmailSender emailSender;
	
	@Inject
	RemetentePadraoDeEMailConfig configuracaoDeEnvioDeEMail;

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
			enviarEMail(evento, participante);		
			
		}
		return rb.entity(inscricao).build();
	}
	
	@Override
	public Response mudaPresenca(long inscricaoId) {
		Inscricao inscricao = inscricaoService.buscarPorId(inscricaoId);
		inscricao.setCompareceu(!inscricao.isCompareceu());
		return Response.ok(inscricao).build();
	}

	/**
	 * Envia o email para o participante de confirmação do registro em um determinado evento,
	 * este envio é feito em uma Thread paralela para evitar onerar a resposta ao usuario por causa de atrasos no envio do e-mail. 
	 * @param evento Evento em que foi inscrito
	 * @param participante Participante inscrito
	 */
	private void enviarEMail(Evento evento, Participante participante) {
		String remetentePadraoDoSistema = configuracaoDeEnvioDeEMail.getEmail();
		String participanteInscrito = participante.getEmail();
		String inscricaoNoEvento = gerarAssuntoDeConfirmacaoDeEmail(evento);
		String mensagemDeConfirmacaoDaInscricao = gerarMensagemDeConfirmacaoDeEmail(evento, participante);
		
		new Thread(new Runnable() {
			public void run() {
				emailSender.criarEmail().de(remetentePadraoDoSistema).para(participanteInscrito).
					tratandoDo(inscricaoNoEvento).comA(mensagemDeConfirmacaoDaInscricao).paraEnviar();
			}
		}).start();
	}

	/**
	 * Gera o assunto confirmando que o participante está inscrito no evento
	 * @param evento
	 * @return
	 */
	private String gerarAssuntoDeConfirmacaoDeEmail(Evento evento) {
		return MessageFormat.format("Você está inscrito no evento {0}", evento.getNome());
	}

	/**
	 * Gera a mensagem confirmando que o participante está inscrito no evento
	 * @param evento
	 * @param participante
	 * @return
	 */
	private String gerarMensagemDeConfirmacaoDeEmail(Evento evento,
			Participante participante) {
		return MessageFormat.format(
		"Parabéns {0}, sua inscrição no evento {1} que ocorrerá no dia {2} no local {3} está confirmada.\n Contamos com a sua participação.",
		participante.getNome(), evento.getNome(), evento.getDataInicio(), evento.getLocal());
	}

}
