package org.jugvale.cfp.client.local;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.ForEvent;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.client.shared.DateConverter;
import org.jugvale.cfp.model.impl.Evento;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gwt.user.client.TakesValue;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLHeadingElement;
import elemental2.dom.MouseEvent;

@Templated("/web/EventoItemWidget.html")
public class EventoItemWidget implements TakesValue<Evento>, IsElement {

	@Inject
	@AutoBound
	DataBinder<Evento> eventoBinder;

	@Bound
	@Inject
	@DataField
	@Named("h4")
	HTMLHeadingElement nome;

	@Bound
	@Inject
	@DataField
	@Named("span")
	HTMLElement descricao;

	@Bound
	@Inject
	@DataField
	@Named("em")
	HTMLElement local;

	@Inject
	@DataField
	@Named("em")
	@Bound(converter = DateConverter.class)
	HTMLElement dataInicio;

	@Inject
	@DataField
	@Named("em")
	@Bound(converter = DateConverter.class)
	HTMLElement dataFim;

	@Inject
	TransitionTo<PaginaDetalhesEvento> paginaDetalhesEvento;

	@EventHandler("nome")
	public void selecionaEvento(final @ForEvent("click") MouseEvent event) {
		Multimap<String, String> state = HashMultimap.create();
		state.put("eventoId", eventoBinder.getModel().getId().toString());
		paginaDetalhesEvento.go(state);
	}

	@Override
	public void setValue(Evento evento) {
		this.eventoBinder.setModel(evento);
	}

	@Override
	public Evento getValue() {
		return eventoBinder.getModel();
	}

}