/**
 * 
 */
package org.jugvale.call4papers.sender;

import java.util.Collection;

/**
 * Interface para abstração de envio de email.
 * @author Daniel
 *
 */
public interface EmailSender {		
	
	public static final String SUCESSO = "1"; 
	
	public DeRemetente criarEmail();	
	
	public interface DeRemetente{
		public ParaDestinatario de(String remetente);
	}	
	
	public interface ParaDestinatario{
		public TratandoDoAssunto para(String destinatario);
		
		public TratandoDoAssunto para(Collection<String> destinatarios);
	}	
	
	public interface TratandoDoAssunto{
		public ComAMensagem tratandoDo(String assunto);
	}	
	
	public interface ComAMensagem{
		public ParaEnviar comA(String mensagem);
	}	
	
	public interface ParaEnviar{
		public String paraEnviar();
	}	

}
