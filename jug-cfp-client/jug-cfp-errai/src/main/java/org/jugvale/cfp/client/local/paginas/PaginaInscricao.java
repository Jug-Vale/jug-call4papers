package org.jugvale.cfp.client.local.paginas;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Validator;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.ForEvent;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.client.local.paginas.evento.PaginaDetalhesEvento;
import org.jugvale.cfp.client.local.shared.Mensagem;
import org.jugvale.cfp.client.local.shared.Mensagem.Tipo;
import org.jugvale.cfp.client.local.shared.NivelConverter;
import org.jugvale.cfp.client.local.shared.Validador;
import org.jugvale.cfp.model.impl.Participante;
import org.jugvale.cfp.rest.EventoResource;
import org.jugvale.cfp.rest.InscricaoResource;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLSelectElement;
import elemental2.dom.MouseEvent;

@Templated
@Page(path = "evento/{eventoId}/{nomeEvento}/inscricao")
public class PaginaInscricao {

	@Inject
	@AutoBound
	DataBinder<Participante> participanteBinder;

	@Inject
	Caller<EventoResource> eventoService;

	@Inject
	Caller<InscricaoResource> inscricaoService;

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

	@Inject
	Validador validador;

	@Inject
	Validator validator;
	
	@Inject
	TransitionTo<PaginaDetalhesEvento> paginaDetalhesEvento;
	
	@PageShown
	public void init() {
		txtNomeEvento.textContent = nomeEvento;
	}


	@EventHandler("btnInscrever")
	public void irParaPaginaInscricao(final @ForEvent("click") MouseEvent evt) {
		Participante participante = participanteBinder.getModel();
		if (validador.validaELancaErroSeInvalido(participante)) {
			inscricaoService.call(r -> {
				event.fire(Mensagem.nova("Inscrição realizada com sucesso", Tipo.SUCESSO));
				limpaCamposTrocaPagina();
			}, (m, t) -> {
				t.printStackTrace();
				event.fire(Mensagem.nova("Erro ao realizar inscrição, verifique os dados enviados.", Tipo.ERRO));
				return false;
			}).inscrever(eventoId, participante);
		}
	}

	private void limpaCamposTrocaPagina() {
		participanteBinder.setModel(new Participante());
		Multimap<String, String> state = HashMultimap.create();
		state.put("eventoId", String.valueOf(eventoId));
		paginaDetalhesEvento.go(state);
	}

}
