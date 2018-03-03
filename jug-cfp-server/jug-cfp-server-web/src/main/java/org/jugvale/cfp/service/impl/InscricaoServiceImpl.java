package org.jugvale.cfp.service.impl;

import javax.enterprise.inject.Default;

import org.jugvale.cfp.model.impl.Inscricao;
import org.jugvale.cfp.service.AbstractService;
import org.jugvale.cfp.service.InscricaoService;

@Default
public class InscricaoServiceImpl extends AbstractService<Inscricao> implements InscricaoService {

	@Override
	public void anularInscricao(Inscricao inscricao) {
		em.remove(inscricao);
	}

}
