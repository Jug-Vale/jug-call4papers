/**
 * 
 */
package org.jugvale.call4papers.model.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Singleton;


/**
 * Classe que representa os atributos da configuração do remetente padrão de e-mail do sistema.
 * @author daniel
 * @author wsiqueir
 *
 */
@Singleton
public class RemetentePadraoDeEMailConfig {
	
	private static final String EMAIL_PROPERTIES = "email.properties";
	private String email;
	private String senha;
	private String hostSmtp;
	private Integer porta;
	private Boolean tlsHabilitado;
	private Boolean usarSSL;
	
	
	public RemetentePadraoDeEMailConfig(){
		carregarInformacoesDoPropeties();
	}

	/**
	 * Carrega as informações do email do property
	 */
	private void carregarInformacoesDoPropeties() {
		Properties emailProperties = new Properties();		
		String propertyName = EMAIL_PROPERTIES;		
		InputStream emailStream = getClass().getClassLoader().getResourceAsStream(propertyName);
		try {
			emailProperties.load(emailStream);		
			this.email = emailProperties.getProperty("remetente");
			this.senha = emailProperties.getProperty("senha");
			this.hostSmtp = emailProperties.getProperty("hostSmtp");
			this.porta = Integer.parseInt(emailProperties.getProperty("porta"));
			this.tlsHabilitado = emailProperties.getProperty("tlsHabilitado").equals("sim") ? true : false;
			this.usarSSL= emailProperties.getProperty("usarSSL").equals("sim") ? true : false;
		} catch (IOException e) {
			//Tratamento de erro feito provisório
			System.err.println("Falha na leitura do arquivo email.properties");
		} catch (Exception e){
			//Tratamento de erro feito provisório
			System.err.println("Falha ao carregar informações do e-mail");
		}
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getHostSmtp() {
		return hostSmtp;
	}	

	public Integer getPorta() {
		return porta;
	}

	public Boolean getTlsHabilitado() {
		return tlsHabilitado;
	}

	public Boolean getUsarSSL() {
		return usarSSL;
	}
	
	

}
