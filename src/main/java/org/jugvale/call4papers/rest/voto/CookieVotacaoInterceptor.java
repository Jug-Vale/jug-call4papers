package org.jugvale.call4papers.rest.voto;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;

/**
 * 
 * Esse interceptor só é invocado quando usamos a anotação VerificaCookieVotacao nos
 * métodos JAX-RS<br />
 * O objetivo é verificar se o usuário já votou, evitando um monte de clique e floodagem.
 * 
 * @author william
 *
 */
public class CookieVotacaoInterceptor implements ContainerRequestFilter {

	@Context
	HttpServletRequest request;

	Logger log = Logger.getLogger(CookieVotacaoInterceptor.class.getCanonicalName());


	public CookieVotacaoInterceptor() {
		super();
	}	
	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {
		// TODO: Adicionar verificação de cookie
	
	}

}