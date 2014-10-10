package org.jugvale.call4papers.rest.voto;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.jugvale.call4papers.inicio.PropriedadesSistema;

/**
 * Aqui determinamos quando a verificação de captcha vai ser invocada
 * 
 * @author william
 *
 */
@Provider
public class CookieVotacaoConfig implements DynamicFeature {

	@Context
	ResourceInfo info;
	
	@Inject
	PropriedadesSistema prop;

	Logger log = Logger.getLogger(CookieVotacaoInterceptor.class.getCanonicalName());

	@Override
	public void configure(ResourceInfo info, FeatureContext ctx) {
		// Se não tiver a anotação pedindo a verificação de cookie, sai fora
		// que é rolê perdido
		boolean invocar = info.getResourceMethod().isAnnotationPresent(
				VerificaCookieVotacao.class);
		if (invocar) {
			log.info("## Cookie de votação será verificado para recurso "
					+ info.getResourceClass().getSimpleName() + " e método "
					+ info.getResourceMethod().getName() + " ##");
			ctx.register(new CookieVotacaoInterceptor());
		}
	}
}
