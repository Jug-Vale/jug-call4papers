package org.jugvale.call4papers.inicio;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jugvale.call4papers.model.enuns.Role;
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
	
	//TODO: Abrir issue para carregar isso de um arquivo. Os dados deverão ser diferentes em teste.

	@PersistenceContext
	EntityManager em;

	Logger log = Logger
			.getLogger(CarregaDadosIniciais.class.getCanonicalName());

	@PostConstruct
	public void carregaDadosIniciais() {
		log.fine("Salvando dados iniciais.");
		Evento evt = new Evento("O Grande Evento",
				"Esse é o melhor evento do mundo, o grande evento...",
				new Date(), new Date(), "Rua dos grandes eventos",
				"http://www.ograndeevento.com", true);
		em.persist(evt);
		Autor maria = new Autor();
		maria.setEmail("meuemail@gmail.com");
		maria.setMiniCurriculo("Grande conhecida no mundo Java...");
		maria.setNome("Maria");
		maria.setTelefone("123456789");
		maria.setSite("www.mariajava.com");
		em.persist(maria);
		Paper javaParaFodoes = new Paper();
		javaParaFodoes.setDataSubmissao(new Date());
		javaParaFodoes.setDescricao("Java para quem ama Java. Java para fodões");
		javaParaFodoes.setTitulo("Java para Fodões");
		javaParaFodoes.setEvento(evt);
		Set<Autor> autores = new HashSet<Autor>();
		autores.add(maria);
		javaParaFodoes.setAutores(autores);
		em.persist(javaParaFodoes);
		// adiciona um usuário administrador
		Usuario adm = new Usuario("adm", "adm123", Role.ADMINISTRADOR);
		em.persist(adm);
		log.fine("Dados iniciais salvos.");
	}
}
