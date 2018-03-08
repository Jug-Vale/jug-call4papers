package org.jugvale.cfp.client.local;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.nav.client.local.NavigationPanel;

import com.google.gwt.user.client.ui.RootPanel;

@EntryPoint
public class AppSetup {
	
	@Inject
	private NavigationPanel navPanel;

	@PostConstruct
	public void init() {
		RootPanel.get("rootPanel").add(navPanel);
	}
}
