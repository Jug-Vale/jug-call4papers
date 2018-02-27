package org.jugvale.cfp.model.builder;

import org.jugvale.cfp.model.enums.Nivel;
import org.jugvale.cfp.model.impl.Participante;

public class ParticipanteBuilder {
	Participante participante;

	public ParticipanteBuilder() {
		this.participante = new Participante();
	}

	public ParticipanteBuilder comNome(String nome) {
		this.participante.setNome(nome);
		return this;
	}

	public ParticipanteBuilder comEmail(String email) {
		this.participante.setEmail(email);
		return this;
	}

	public ParticipanteBuilder comEmpresa(String empresa) {
		this.participante.setEmpresa(empresa);
		return this;
	}

	public ParticipanteBuilder comInstituicao(String instituicao) {
		this.participante.setInstituicao(instituicao);
		return this;
	}

	public ParticipanteBuilder comRg(String rg) {
		this.participante.setRg(rg);
		return this;
	}

	public ParticipanteBuilder comNivel(Nivel nivel) {
		this.participante.setNivel(nivel);
		return this;
	}

	public Participante build() {
		return this.participante;
	}

}
