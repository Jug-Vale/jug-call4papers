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

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jugvale.cfp.model.impl.Evento;
import org.jugvale.cfp.rest.EventoResource;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

@Templated("#main")
@Page(role = DefaultPage.class)
public class App extends Composite {

	@Inject
	@DataField
	HTML lstEventos;

	@Inject
	@DataField
	Button btn;

	@Inject
	Caller<EventoResource> eventoService;

	@PostConstruct
	public void dosomething() {
		for (int i = 0; i < 10; i++) {
			lstEventos.setHTML(lstEventos.getHTML() + "<br />" + i);
		}
	}

	@EventHandler("btn")
	public void loadEventos(ClickEvent evt) {
		lstEventos.setHTML(new Date().toString());
		eventoService.call((List<Evento> eventos) -> {
			for (Evento e : eventos ) {
				lstEventos.setHTML(lstEventos.getHTML() + " <br />" + e.getNome());
			}

		}, (String message, Throwable throwable) -> {
			lstEventos.setHTML(message);
			return false;
		}).listarTodos();
	}

}
