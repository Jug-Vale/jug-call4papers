/**
 * Copyright (C) 2016 Red Hat, Inc. and/or its affiliates.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jugvale.cfp.client.local;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.databinding.client.components.ListComponent;
import org.jboss.errai.databinding.client.components.ListContainer;
import org.jboss.errai.ioc.client.api.LoadAsync;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.rest.EventoResource;

import elemental2.dom.HTMLDivElement;

@Templated("/web/App.html#main")
@Page(role = DefaultPage.class)
public class App {

	@Inject
	Caller<EventoResource> eventoService;

	@Inject
	@DataField
	@ListContainer("listaEventosEmAberto")
	private ListComponent<Evento, EventoItemWidget> listaEventosEmAberto;

	@Inject
	@DataField
	@ListContainer("listaEventosFechados")
	private ListComponent<Evento, EventoItemWidget> listaEventosFechados;

	@Inject
	@DataField
	private HTMLDivElement divEventosAbertos;

	@Inject
	@DataField
	private HTMLDivElement divEventosFechados;

	@PageShown
	public void loadEventos() {
		eventoService
				.call((List<Evento> eventos) -> mostraEventos(eventos), (String message, Throwable throwable) -> false)
				.listarTodos();
	}

	private void mostraEventos(List<Evento> eventos) {
		List<Evento> eventosAbertos = eventos.stream().filter(e -> e.isAceitandoTrabalhos() || e.isInscricoesAbertas())
				.collect(Collectors.toList());
		List<Evento> eventosFechados = eventos.stream()
				.filter(e -> !e.isAceitandoTrabalhos() && !e.isInscricoesAbertas()).collect(Collectors.toList());
		if (eventosAbertos.size() > 0) {
			divEventosAbertos.hidden = false;
			listaEventosEmAberto.setValue(eventosAbertos);
		} else {
			divEventosAbertos.hidden = true;
		}
		if (eventosFechados.size() > 0) {
			divEventosFechados.hidden = false;
			listaEventosFechados.setValue(eventosFechados);
		} else {
			divEventosFechados.hidden = true;
		}

	}

}
