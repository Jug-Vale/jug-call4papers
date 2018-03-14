package org.jugvale.cfp.client.local;

import java.util.Collections;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.logging.util.Console;
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
import org.jugvale.cfp.client.local.shared.Mensagem;
import org.jugvale.cfp.client.local.shared.Mensagem.Tipo;
import org.jugvale.cfp.client.local.shared.Validador;
import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.model.impl.Paper;
import org.jugvale.cfp.rest.EventoResource;
import org.jugvale.cfp.rest.PaperResource;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLHeadingElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTextAreaElement;
import elemental2.dom.MouseEvent;

@Page(path = "evento/{eventoId}/{nomeEvento}/submeter")
@Templated("/web/PaginaNovoPaper.html")
public class PaginaSubmeterPaper {

	@PageState
	long eventoId;

	@PageState
	String nomeEvento;

	@Inject
	Caller<PaperResource> paperService;

	@Inject
	Caller<EventoResource> eventoService;

	@Inject
	@AutoBound
	DataBinder<Paper> paperBinder;

	@Bound
	@Inject
	@DataField
	private HTMLInputElement titulo;

	@Bound
	@Inject
	@DataField
	private HTMLTextAreaElement descricao;

	@Inject
	@DataField
	@Named("h2")
	private HTMLHeadingElement tituloPagina;

	@Inject
	@DataField
	HTMLButtonElement btnSalvar;
	
	@Inject
	@DataField
	FormAutor formAutor;

	private Evento evento;
	
	@Inject
	private Event<Mensagem> eventoMensagem;
	
	@Inject
	TransitionTo<PaginaDetalhesEvento> paginaDetalhesEvento;
	
	@Inject Validador validador;

	@PageShown
	public void carregaCoisas() {
		eventoService.call((Evento e) -> setEvento(e), this::erroCarregarEvento).buscaPorId(eventoId);
	}

	private Object setEvento(Evento e) {
		this.evento = e;
		tituloPagina.textContent = "Submeter proposta para " + e.getNome();
		return null;
	}

	@EventHandler("btnSalvar")
	public void salva(final @ForEvent("click") MouseEvent ev) {
		Paper paper = paperBinder.getModel();
		paper.setEvento(evento);
		paper.setTipo(org.jugvale.cfp.model.enums.Tipo.PALESTRA);
		paper.setAutores(Collections.singleton(formAutor.getValue()));
		if(validador.validaELancaErroSeInvalido(paper)) { 
			paperService.call(e -> paperSubmetidoComSucesso(), this::erroSubmeterPaper).criar(paper);
		}
	}
	
	private Object paperSubmetidoComSucesso() {
		eventoMensagem.fire(Mensagem.nova("Paper submetido com sucesso!", Tipo.SUCESSO));
		Multimap<String, String> state = HashMultimap.create();
		state.put("eventoId", String.valueOf(eventoId));
		paginaDetalhesEvento.go(state);
		return null;
	}

	public boolean erroCarregarEvento(Object obj, Throwable e) {
		eventoMensagem.fire(Mensagem.nova("Erro ao carregar evento: " + e.getMessage(), Tipo.ERRO));
		return false;
	}
	
	public boolean erroSubmeterPaper(Object obj, Throwable e) {
		eventoMensagem.fire(Mensagem.nova("Erro ao submeter paper: " + e.getMessage(), Tipo.ERRO));
		return false;
	}

}