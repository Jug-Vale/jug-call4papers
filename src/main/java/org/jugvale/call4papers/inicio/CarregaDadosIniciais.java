package org.jugvale.call4papers.inicio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.model.impl.Usuario;

/**
 * 
 * Adiciona dados iniciais na aplicação no momento de deploy. O banco de dados
 * deve estar limpo, caso contrário, devemos verificar antes de adicionar dados
 * 
 * @author wsiqueir
 * 
 */
@Singleton
@Startup
public class CarregaDadosIniciais {

	@PersistenceContext
	EntityManager em;

	final String CAMINHO_PROPERTIES_ADM = "/admin.properties";

	Logger log = Logger
			.getLogger(CarregaDadosIniciais.class.getCanonicalName());

	@PostConstruct
	public void carregaDadosIniciais() throws IOException, URISyntaxException {
		log.info("#### Salvando dados iniciais. #####");

		log.info("#### Carregando dados do usuário administrador. #####");
		URL propResource = getClass().getResource(CAMINHO_PROPERTIES_ADM);
		if (propResource != null) {
			Properties dadosAdmin = new Properties();
			dadosAdmin.load(new FileInputStream(propResource.getFile()));
			dadosAdmin.keySet().forEach(
					u -> {
						String login = String.valueOf(u);
						String senha = dadosAdmin.getProperty(login);
						Usuario administrador = Usuario.administrador()
								.comLogin(login).comSenha(senha).build();
						em.persist(administrador);
						log.info("#### Usuário " + login
								+ " persistido com sucesso. #####");
					});

		} else {
			log.info("#### Arquivo de propriedades não encontrado, não será carregado o administrador inicial. Faça o redeploy criando um properties com login=senha em \"src/main/resources/"
					+ CAMINHO_PROPERTIES_ADM + "\". #### ");
		}
		log.info("#### Carregando dados de demonstração #####");
		Usuario mariaUsr = Usuario.autor().comLogin("Maria").comSenha("mariah")
				.build();

		em.persist(mariaUsr);

		Usuario joseUsr = Usuario.autor().comLogin("Josevaldo")
				.comSenha("jose123valdo").build();

		em.persist(joseUsr);

		Evento grandeEvento = Evento
				.newEvento()
				.comNome("O Grande Evento")
				.comDescricao(
						"Esse é o melhor evento do mundo, o grande evento...")
				.comDataInicio(new Date()).comDataFim(new Date())
				.noLocal("Rua dos grandes eventos")
				.comSite("http://www.ograndeevento.com").aceitandoTrabalhos()
				.build();

		em.persist(grandeEvento);

		Autor maria = Autor.newAutor().comNome("Maria")
				.comEmail("meuemail@gmail.com").comSite("www.mariajava.com")
				.comTelefone("(99) 9 9999-9999")
				.comMiniCV("Grande conhecida no mundo Java...")
				.comUsuario(mariaUsr).build();

		em.persist(maria);

		Autor jose = Autor.newAutor().comNome("Josevaldo")
				.comEmail("josevaldoJava@gmail.com")
				.comSite("www.josevaldojava.com")
				.comTelefone("(99) 9 9999-9999")
				.comMiniCV("Mestre no mundo Java...").comUsuario(joseUsr)
				.build();

		em.persist(jose);

		Paper javaParaJaveiros = Paper.palestra().submetidoNaData(new Date())
				.comDescricao("Java para quem ama Java. Java para javeiros")
				.comTitulo("Java para javeiros").noEvento(grandeEvento)
				.comAutor(maria).comAutor(jose).build();

		em.persist(javaParaJaveiros);

		log.info("#### Dados iniciais salvos. ####");
	}
}