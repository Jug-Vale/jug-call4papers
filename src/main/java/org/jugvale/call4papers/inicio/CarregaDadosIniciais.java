package org.jugvale.call4papers.inicio;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.service.impl.EventoService;

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
	
	@Inject
	EventoService eventoService;
		
	Logger log = Logger
			.getLogger(CarregaDadosIniciais.class.getCanonicalName());

	@PostConstruct
	public void carregaDadosIniciais() {
		
		if(eventoService.todos().size() > 1) {
			log.info("#### Banco de dados não está vazio. Dados não serão carregados. #####");
			return;
		}
		
		log.info("#### Carregando dados de demonstração #####");

		Evento grandeEvento = Evento
				.newEvento()
				.comNome("O Grande Evento")
				.comDescricao(
						"Esse é o melhor evento do mundo, o grande evento...")
				.comDataInicio(new Date()).comDataFim(new Date())
				.noLocal("Rua Tsunessaburo Makiguti,157, São José dos Campos, SP, Brasil")
				.comSite("http://www.ograndeevento.com").aceitandoTrabalhos()
				.build();

		em.persist(grandeEvento);

		Autor maria = Autor.newAutor().comNome("Maria")
				.comEmail("meuemail@gmail.com").comSite("www.mariajava.com")
				.comTelefone("(99) 9 9999-9999")
				.comMiniCV("Grande conhecida no mundo Java...")
				.build();

		em.persist(maria);

		Autor jose = Autor.newAutor().comNome("Josevaldo")
				.comEmail("josevaldoJava@gmail.com")
				.comSite("www.josevaldojava.com")
				.comTelefone("(99) 9 9999-9999")
				.comMiniCV("Mestre no mundo Java...")
				.build();

		em.persist(jose);

		Paper javaParaJaveiros = Paper.palestra().submetidoNaData(new Date())
				.comDescricao("Java para quem ama Java. Java para javeiros")
				.comTitulo("Java para javeiros").noEvento(grandeEvento)
				.comAutor(maria).comAutor(jose).build();

		em.persist(javaParaJaveiros);
		
		Paper paper = Paper.handsOn().submetidoNaData(new Date())
				.comDescricao(" ac dolor labore sapien varius ac maecenas ligula feugiat sollicitudin risus donec "
								+ "natoque etiam laborum ullamcorper eu montes libero neque ac ac ante integer proin at non dolor "
								+ "vestibulum voluptatum ")
				.comTitulo("Rest Na Pratica").noEvento(grandeEvento)
				.comAutor(jose).aceito().build();

		em.persist(paper);

		log.info("#### Dados iniciais salvos. ####");
	}
}