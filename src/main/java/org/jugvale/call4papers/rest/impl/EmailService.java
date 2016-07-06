package org.jugvale.call4papers.rest.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jugvale.call4papers.model.config.RemetentePadraoDeEMailConfig;
import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Participante;
import org.jugvale.call4papers.sender.EmailSender;

@Stateless
public class EmailService {
	
	private static final String FORMATO_DATA = "dd/MM/yyyy hh:mm";

	@Inject
	EmailSender emailSender;
	
	@Inject
	RemetentePadraoDeEMailConfig configuracaoDeEnvioDeEMail;
	
	/**
	 * Envia o email para o participante de confirmação do registro em um determinado evento,
	 * este envio é feito em uma Thread paralela para evitar onerar a resposta ao usuario por causa de atrasos no envio do e-mail. 
	 * @param evento Evento em que foi inscrito
	 * @param participante Participante inscrito
	 */
	
	@Asynchronous
	public void enviarEMail(Evento evento, Participante participante) {
		String remetentePadraoDoSistema = configuracaoDeEnvioDeEMail.getEmail();
		String participanteInscrito = participante.getEmail();
		String inscricaoNoEvento = gerarAssuntoDeConfirmacaoDeEmail(evento);
		String mensagemDeConfirmacaoDaInscricao = gerarMensagemDeConfirmacaoDeEmail(evento, participante);
		

		emailSender.criarEmail().de(remetentePadraoDoSistema).para(participanteInscrito).
			tratandoDo(inscricaoNoEvento).comA(mensagemDeConfirmacaoDaInscricao).paraEnviar();
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
		// faz o parse da data, pois o MessageFormate usa o locale do server para fazer o parse, precisamos da data cadastrada como foi
		String dataFormatada = new SimpleDateFormat(FORMATO_DATA).format(evento.getDataInicio());
		return MessageFormat.format(
		"Parabéns {0}, sua inscrição no evento {1} que ocorrerá no dia {2} no local {3} está confirmada.\n Contamos com a sua participação.",
		participante.getNome(), evento.getNome(), dataFormatada, evento.getLocal());
	}

}
