package org.jugvale.cfp.service.impl;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;

import org.jugvale.cfp.model.impl.Inscricao;
import org.jugvale.cfp.service.Service;

@Default @Any
public class InscricaoService extends Service<Inscricao> {

	public void anularInscricao(Inscricao inscricao) {
		em.remove(inscricao);
	}

}
