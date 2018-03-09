package org.jugvale.cfp.client.local;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.ForEvent;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.client.local.shared.Mensagem;
import org.jugvale.cfp.client.local.shared.Mensagem.Tipo;
import org.jugvale.cfp.client.local.shared.NivelConverter;
import org.jugvale.cfp.model.impl.Participante;
import org.jugvale.cfp.rest.EventoResource;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLSelectElement;
import elemental2.dom.MouseEvent;

@Templated("/web/PaginaInscricao.html")
@Page(path = "evento/{eventoId}/{nomeEvento}/inscricao")
public class PaginaInscricao {

	@Inject
	@AutoBound
	DataBinder<Participante> participanteBinder;

	@Inject
	Caller<EventoResource> eventoService;

	@PageState
	long eventoId;

	@PageState
	String nomeEvento;

	@Inject
	@DataField
	@Named("em")
	HTMLElement txtNomeEvento;

	@Bound
	@Inject
	@DataField
	HTMLInputElement nome;

	@Bound
	@Inject
	@DataField
	HTMLInputElement email;

	@Bound
	@Inject
	@DataField
	HTMLInputElement rg;

	@Bound
	@Inject
	@DataField
	HTMLInputElement instituicao;

	@Bound
	@Inject
	@DataField
	HTMLInputElement empresa;

	@Inject
	@DataField
	@Bound(converter = NivelConverter.class)
	HTMLSelectElement nivel;

	@Inject
	@DataField
	HTMLInputElement btnInscrever;

	@Inject
	private Event<Mensagem> event;

	@PageShown
	public void init() {
		txtNomeEvento.textContent = nomeEvento;
	}

	@EventHandler("btnInscrever")
	public void irParaPaginaInscricao(final @ForEvent("click") MouseEvent evt) {
		eventoService.call(r -> {
			event.fire(Mensagem.nova("Inscrição realizada com sucesso", Tipo.SUCESSO));
			limpaCampos();
		}, (m, t) -> {
			event.fire(Mensagem.nova("Erro ao realizar inscrição: " + t.getMessage(), Tipo.ERRO));
			return false;
		}).inscreverParticipante(participanteBinder.getModel(), eventoId);
	}

	private void limpaCampos() {
		participanteBinder.setModel(new Participante());
	}

}
