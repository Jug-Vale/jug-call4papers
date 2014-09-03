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

	Logger log = Logger.getLogger(CarregaDadosIniciais.class.getCanonicalName());

	@PostConstruct
	public void carregaDadosIniciais() {
		log.fine("Salvando dados iniciais.");
		
		// adiciona um usuário administrador
		Usuario adm = new Usuario("adm", "adm123", Role.ADMINISTRADOR);
		em.persist(adm);
		System.out.println(adm.getSenha());
		
		// Um usuário para a Maria
		Usuario mariaUsr = new Usuario("maria", "maria123", Role.AUTOR);
		em.persist(mariaUsr);
		
		Evento grandeEvento =  new Evento.Builder()
							   .comNome("O Grande Evento")
							   .comDescricao("Esse é o melhor evento do mundo, o grande evento...")
							   .comDataInicio(new Date())
							   .comDataFim(new Date())
							   .noLocal("Rua dos grandes eventos")
							   .comSite("http://www.ograndeevento.com")
							   .aceitandoTrabalhos()
							   .build();
						
		em.persist(grandeEvento);
		
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
		javaParaFodoes.setEvento(grandeEvento);
		Set<Autor> autores = new HashSet<Autor>();
		autores.add(maria);
		javaParaFodoes.setAutores(autores);
		em.persist(javaParaFodoes);
		log.fine("Dados iniciais salvos.");
	}
}