package org.jugvale.cfp.service;

import org.jugvale.cfp.model.impl.Inscricao;

public interface InscricaoService extends Service<Inscricao> {

	void anularInscricao(Inscricao inscricao);

}
