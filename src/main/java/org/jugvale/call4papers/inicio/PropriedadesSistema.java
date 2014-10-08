package org.jugvale.call4papers.inicio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
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

	final String CAMINHO_PROPERTIES_ADM = "/config.properties";

	Logger log = Logger.getLogger(PropriedadesSistema.class.getCanonicalName());

	private Properties propriedades;

	@PostConstruct
	private void carregaPropriedades() throws FileNotFoundException,
			IOException {
		propriedades = new Properties();
		URL file = PropriedadesSistema.class
				.getResource(CAMINHO_PROPERTIES_ADM);
		if (file != null) {
			propriedades.load(new FileInputStream(file.getPath()));
		} else {
			log.info("Não foi possível carregar as propriedades do sistema! Crie um arquivo chamado "
					+ CAMINHO_PROPERTIES_ADM + " e faça o redeploy");
		}

	}

	public Optional<?> get(String nomePropriedade) {
		return Optional.of(propriedades.get(nomePropriedade));

	}

}
