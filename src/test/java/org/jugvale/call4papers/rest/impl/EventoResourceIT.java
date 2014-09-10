package org.jugvale.call4papers.rest.impl;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.jugvale.call4papers.rest.util.Constantes.ID_MAX;
import static org.jugvale.call4papers.rest.util.Constantes.SERVICES_CONTEXT;
import static org.jugvale.call4papers.rest.utils.RESTUtils.getMessage404;

import java.util.Date;

import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.rest.TestResourceDefault;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventoResourceIT implements TestResourceDefault {

	private static final String EVENTO_CONTEXT = SERVICES_CONTEXT.concat("/evento");
	
	private Evento evento;
	private String jsonEvento;
	
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
		
		ObjectMapper mapper = new ObjectMapper();
		jsonEvento = mapper.writeValueAsString(evento);
	}
	
	@Test
	@Override
	public void deveRetornar201AoCadastrar() {  /* TODO: IMPLEMENTAR */  }

	@Test
	@Override
	public void deveRetornarOkAoApagar() {  /* TODO: IMPLEMENTAR */  }

	@Test
	@Override
	public void deveRetornarOkAoAtualizar() {  /* TODO: IMPLEMENTAR */  }
	
	@Test
	@Override
	public void deveRetornarOkAoBuscarLista() {
		get(EVENTO_CONTEXT).
			then().
				body(notNullValue()).
				statusCode( equalTo(200) );
	}
	
	@Test
	@Override
	public void deveRetornarOkAoBuscarPorId() {
		get(EVENTO_CONTEXT + "/4").
			then().
				body( notNullValue() ).
				statusCode( equalTo(200) );
	}
	
	@Test
	public void deveRetornarStatusOkParaListaDePapersPorEvento() {
		get(EVENTO_CONTEXT + "/4/papers").
			then().assertThat().
				body(notNullValue()).
				statusCode(200);
	}
	
	@Test
	@Override
	public void deveRetornarNotFoundAoBuscarPorId() {
		get(EVENTO_CONTEXT + "/" + ID_MAX).
			then().
				body( equalTo( getMessage404(ID_MAX) ) ).
				statusCode( equalTo(404) );
	}
	
	@Test
	public void deveRetornarStatusNotFoundParaListaDePapersPorEvento() {
		get(EVENTO_CONTEXT + "/" + ID_MAX +"/papers").
			then().assertThat().
				body( equalTo( getMessage404(ID_MAX) ) ).
				statusCode(404);
	}
	
	@Test
	@Override
	public void deveRetornarNotFoundAoApagar() {  /* TODO: IMPLEMENTAR */  }

	@Test
	@Override
	public void deveRetornarNotFoundAoAtualizar() { /* TODO: IMPLEMENTAR */  }

	@Test
	@Override
	public void deveRetornarUnauthorizedAoCadastrar() {
		given().
			contentType(JSON).
			body(jsonEvento).
		when().
			post(EVENTO_CONTEXT).
		then().
			statusCode( equalTo(401) );
	}
	
	@Test
	@Override
	public void deveRetornarStatusUnauthorizedAoAtualizar() {
		given().
			contentType(JSON).
			body(jsonEvento).
		when().
			put(EVENTO_CONTEXT + "/4").
		then().
			statusCode( equalTo(401) );
	}
	
	@Test
	@Override
	public void deveRetornarStatusUnauthorizedAoApagar() {
		given().
			contentType(JSON).
			body(jsonEvento).
		when().
			delete(EVENTO_CONTEXT + "/4").
		then().statusCode( equalTo(401) );
	}

}
