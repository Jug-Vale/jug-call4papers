package org.jugvale.call4papers.inicio;

import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * 
 * EJB Singleton que carrega as propriedades configuraveis e secretas do sistema <br />
 * 
 * @author william
 *
 */
@Singleton
@Startup
public class PropriedadesSistema {

	private final String CAPTCHA_CHAVE_PUBLICA = "CAPTCHA_CHAVE_PUBLICA";
	private final String CAPTCHA_CHAVE_PRIVADA = "CAPTCHA_CHAVE_PRIVADA";

	public String chavePrivadaCaptcha() {
		return System.getProperty(CAPTCHA_CHAVE_PRIVADA);
	}

	public String chavePublicaCaptcha() {
		return System.getProperty(CAPTCHA_CHAVE_PUBLICA);

	}

}
