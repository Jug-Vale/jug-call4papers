package org.jugvale.call4papers.rest.impl;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.http.ContentType.*;
import static org.jugvale.call4papers.rest.util.Constantes.SERVICES_CONTEXT;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.jugvale.call4papers.model.impl.Evento;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Pedro Hos
 *
 */
public class EventoResourceImplITest {

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
	public void deveRetornarOkParaListaEventos() {
		get(EVENTO_CONTEXT).then().assertThat().body(notNullValue()).statusCode( equalTo(200) );
	}
	
	@Test
	public void deveRetornarEventoPorID() {
		get(EVENTO_CONTEXT + "/4").then().assertThat().body(notNullValue()).statusCode( equalTo(200) );
	}
	
	@Test
	public void deveRetornarNotFoundAoBuscarEventoPorId() {
		get(EVENTO_CONTEXT + "/" + Long.MAX_VALUE).then().assertThat().body(notNullValue()).statusCode( equalTo(404) );
	}

	@Test
	public void deveRetornarStatusUnauthorizedAoCadastrarEvento() {
		given().contentType(JSON).body(jsonEvento).when().post(EVENTO_CONTEXT).then().statusCode( equalTo(401) );
	}
	
	@Test
	public void deveRetornarStatusUnauthorizedAoAtualizarEvento() {
		given().contentType(JSON).body(jsonEvento).when().put(EVENTO_CONTEXT + "/4").then().statusCode( equalTo(401) );
	}
	
	@Test
	public void deveRetornarStatusUnauthorizedAoApagarEvento() {
		given().contentType(JSON).body(jsonEvento).when().delete(EVENTO_CONTEXT + "/4").then().statusCode( equalTo(401) );
	}
}
