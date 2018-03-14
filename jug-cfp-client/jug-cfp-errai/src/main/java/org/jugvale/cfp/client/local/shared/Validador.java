package org.jugvale.cfp.client.local.shared;

import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.jugvale.cfp.client.local.shared.Mensagem.Tipo;

@ApplicationScoped
public class Validador {

	@Inject
	Validator validator;

	@Inject
	private Event<Mensagem> event;

	public boolean validaELancaErroSeInvalido(Object alvo) {
		Set<ConstraintViolation<Object>> validations = validator.validate(alvo);
		if (validations.size() == 0) {
			return true;
		} else {
			String validationMsgs = validations.stream().map(v -> v.getMessage())
					.collect(Collectors.joining("</li><li>", "<li>", "</li>"));
			event.fire(Mensagem.nova("Há erros no seu formulário: <br /><ul>" + validationMsgs + "</ul>", Tipo.ERRO));
			return false;
		}
	}

}
