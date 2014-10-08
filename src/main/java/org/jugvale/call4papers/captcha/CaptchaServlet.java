package org.jugvale.call4papers.captcha;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jugvale.call4papers.inicio.PropriedadesSistema;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;

@WebServlet("/gerar-captcha.html")
public class CaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	PropriedadesSistema prop;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ReCaptcha c = ReCaptchaFactory.newReCaptcha(prop.chavePublicaCaptcha(),
				prop.chavePrivadaCaptcha(), false);
		final PrintWriter writer = resp.getWriter();
		writer.print(c.createRecaptchaHtml(null, null));
	}

}
