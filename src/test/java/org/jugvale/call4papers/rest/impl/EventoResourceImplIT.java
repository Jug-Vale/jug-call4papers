package org.jugvale.call4papers.rest.impl;

import java.util.Date;

import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.Response.Status.*;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jugvale.call4papers.model.impl.Evento;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EventoResourceImplIT {


	public static final String SERVICES_CONTEXT = "http://localhost:8080/jug-call4papers/rest/v1";
	private static final String EVENTO_CONTEXT = SERVICES_CONTEXT.concat("/evento");
	
	private Evento evento;
	
	@Before
	public void setUp() throws Exception {
		evento =  Evento.newEvento()
							   .comNome("O Grande Evento Segunda Edição")
							   .comDescricao("Esse é o melhor evento do mundo, o grande evento, " +
									   		 "se o primeiro foi melhor, o segundo melhor ainda...")
							   .comDataInicio(new Date())
							   .comDataFim(new Date())
							   .noLocal("Rua dos grandes eventos, numero: 88")
							   .comSite("http://www.ograndeevento.com")
							   .aceitandoTrabalhos()
							   .build();
	}

	@Test
	public void deveRetornarOkParaListaEventos() throws Exception {
		ClientResponse<?> response = new ClientRequest(EVENTO_CONTEXT).get();
		assertTrue(OK.equals(fromStatusCode(response.getStatus())));
	}
	

	@Test
	public void deveRetornarStatusNaoAutorizado() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(evento);
		
		ClientResponse<?> response = new ClientRequest(EVENTO_CONTEXT)
											.body(MediaType.APPLICATION_JSON, json)
											.post();
		
		assertTrue(UNAUTHORIZED.equals(fromStatusCode(response.getStatus())));
	}

}
