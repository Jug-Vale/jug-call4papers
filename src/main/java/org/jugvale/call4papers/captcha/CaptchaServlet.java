package org.jugvale.call4papers.captcha;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;

@WebServlet
public class CaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ReCaptcha c = ReCaptchaFactory.newReCaptcha("YOUR_PUBLIC_KEY",
				"YOUR_PRIVATE_KEY", false);
		PrintWriter writer = resp.getWriter();
		writer.write(c.createRecaptchaHtml(null, null));
		writer.close();
		super.doGet(req, resp);
	}

}
