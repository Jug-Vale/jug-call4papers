package org.jugvale.call4papers.rest.captcha;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;

import net.tanesha.recaptcha.ReCaptchaImpl;

/**
 * 
 * Esse interceptor só é invocado quando usamos a anotação VerificaCaptcha nos
 * métodos JAX-RS<br />
 * O objetivo é verificar o captcha entrado e pular essa verificação se o
 * usuário for administrador
 * 
 * @author william
 *
 */
public class CaptchaInterceptor implements ContainerRequestFilter {

	@Context
	HttpServletRequest request;

	Logger log = Logger.getLogger(CaptchaInterceptor.class.getCanonicalName());

	private String chavePrivadaCaptcha;

	public CaptchaInterceptor() {
		super();
	}

	public CaptchaInterceptor(String chavePrivadaCaptcha) {
		super();
		this.chavePrivadaCaptcha = chavePrivadaCaptcha;
	}

	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {
		boolean ehAdm = ctx.getSecurityContext().isUserInRole("ADMINISTRADOR");
		boolean passouCaptcha = false;
		log.info("## Criando novo Paper ##");
		if (!ehAdm) {
			String remoteAddr = request.getRemoteAddr();
			ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
			reCaptcha.setPrivateKey(chavePrivadaCaptcha);
			String challenge = request
					.getParameter("recaptcha_challenge_field");
			String uresponse = request.getParameter("recaptcha_response_field");
			if (challenge != null && uresponse != null) {
				passouCaptcha = reCaptcha.checkAnswer(remoteAddr, challenge,
						uresponse).isValid();
			}
		}
		if (!passouCaptcha) {
			throw new NotAuthorizedException("Captcha errado ou parâmetros não informados...");
		}

	}

}
