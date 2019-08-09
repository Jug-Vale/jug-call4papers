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

package org.jugvale.cfp.client.local.evento;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.databinding.client.components.ListComponent;
import org.jboss.errai.databinding.client.components.ListContainer;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.client.local.shared.Mensagem;
import org.jugvale.cfp.client.local.shared.Mensagem.Tipo;
import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.rest.EventoResource;

import elemental2.dom.HTMLDivElement;

@Templated
@Page(role = DefaultPage.class)
public class ListaTodosEventos {

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

	@Inject
	private Event<Mensagem> eventoMensagem;

	@PostConstruct
	private void init() {
		divEventosAbertos.hidden = true;
		divEventosFechados.hidden = true;
	}
	

	@PageShown
	public void loadEventos() {
		eventoService.call((List<Evento> eventos) -> mostraEventos(eventos), this::mostraErro).listarTodos();
	}

	public boolean mostraErro(Object obj, Throwable e) {
		eventoMensagem.fire(Mensagem.nova("Erro ao listar eventos " + e.getMessage(), Tipo.ERRO));
		return false;
	}

	private void mostraEventos(List<Evento> eventos) {
		Date hoje = new Date();
		Collections.sort(eventos, Collections.reverseOrder((e1, e2) -> 
			e1.getDataFim().compareTo(e2.getDataFim())
		));
		List<Evento> eventosAbertos = eventos.stream().filter(e -> e.getDataFim().after(hoje))
				.collect(Collectors.toList());
		List<Evento> eventosFechados = eventos.stream()
				.filter(e ->e.getDataFim().before(hoje)).collect(Collectors.toList());
		if (eventosAbertos.size() > 0) {
			divEventosAbertos.hidden = false;
			listaEventosEmAberto.setValue(eventosAbertos);
		}
		if (eventosFechados.size() > 0) {
			divEventosFechados.hidden = false;
			listaEventosFechados.setValue(eventosFechados);
		}
	}

}
