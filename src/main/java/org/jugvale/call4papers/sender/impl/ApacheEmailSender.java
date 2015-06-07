/**
 * 
 */
package org.jugvale.call4papers.sender.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.mail.SimpleEmail;
import org.jugvale.call4papers.model.config.RemetentePadraoDeEMailConfig;
import org.jugvale.call4papers.sender.EmailSender;

/**
 * 
 * Implementação da interface {@link EmailSender}, enviando e-mails utilizando a API ApacheCommons para o envio de e-mail.
 * Caso outroa tecnologia vá ser usada, esse cara pode até ser abstraido na parte que não envolve a tecnologia especifica que sera utilzada.
 * @author Daniel
 *
 */
public class ApacheEmailSender implements EmailSender {
	
	@Inject
	RemetentePadraoDeEMailConfig configuracaoDeEnvioDeEMail;

	private class Email implements EmailSender.DeRemetente, EmailSender.ParaDestinatario, EmailSender.TratandoDoAssunto, EmailSender.ComAMensagem, EmailSender.ParaEnviar{
		
		private String remetente;
		private Collection<String> destinatarios;
		private String assunto;
		private String mensagem;
		
		
		@Override
		public String paraEnviar() {	
			
			try{
				SimpleEmail email = new SimpleEmail();
				email.setAuthentication(configuracaoDeEnvioDeEMail.getEmail(), configuracaoDeEnvioDeEMail.getSenha());
				email.setHostName(configuracaoDeEnvioDeEMail.getHostSmtp());
				email.setSmtpPort(configuracaoDeEnvioDeEMail.getPorta());	
				email.setStartTLSEnabled(configuracaoDeEnvioDeEMail.getTlsHabilitado());
				email.										
					setFrom(this.remetente).//De
					addTo(this.destinatarios.toArray(new String[this.destinatarios.size()])).//Para
					setSubject(this.assunto).//Assunto
					setMsg(this.mensagem).//Mensagem
					send();//Enviar				
			} catch(Exception e){
				//Tratamento de erro feito provisório
				System.err.println(MessageFormat.format("Falha no envio de e-mail para {0} o seguinte erro ocorreu: {1}",this.destinatarios, e.getMessage()));
			}
			return EmailSender.SUCESSO;
		}

		@Override
		public ParaEnviar comA(String mensagem) {
			this.mensagem = mensagem;
			return this;
		}

		@Override
		public ComAMensagem tratandoDo(String assunto) {
			this.assunto = assunto;
			return this;
		}

		@Override
		public TratandoDoAssunto para(String destinatario) {
			if(this.destinatarios == null) this.destinatarios = new ArrayList<>();
			this.destinatarios.add(destinatario);
			return this;
		}

		@Override
		public TratandoDoAssunto para(Collection<String> destinatarios) {
			this.destinatarios = destinatarios;
			return this;
		}

		@Override
		public ParaDestinatario de(String remetente) {
			this.remetente = remetente;
			return this;
		}

		
	}
	
	@Override
	public DeRemetente criarEmail() {
		Email email = new Email();
		return email;
	}
	
}
