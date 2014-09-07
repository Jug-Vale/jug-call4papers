package org.jugvale.call4papers.rest.impl;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.jugvale.call4papers.rest.util.Constantes.ID_MAX;
import static org.jugvale.call4papers.rest.util.Constantes.SERVICES_CONTEXT;
import static org.jugvale.call4papers.rest.utils.RESTUtils.getMessage404;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.rest.TestResourceDefault;
import org.junit.Before;
import org.junit.Test;

public class PaperResourceIT implements TestResourceDefault {

	private static final String PAPER_CONTEXT = SERVICES_CONTEXT + "/paper";

	private Paper paper;
	private String jsonPaper;

	@Before
	public void setUp() throws Exception {
		paper = Paper.newPapper().aceito().comAutor(Autor.newAutor().build())
				.comDescricao("DESCRICAO").comTitulo("").miniCurso()
				.noEvento(Evento.newEvento().build()).submetidoEm(new Date())
				.build();

		jsonPaper = new ObjectMapper().writeValueAsString(paper);
	}
	
	@Test
	@Override
	public void deveRetornarOkAoBuscarPorId() {

		get(PAPER_CONTEXT + "/7").
			then().assertThat().
					body(notNullValue()).
					body("autores.nome", containsInAnyOrder("Maria", "Josevaldo")).
					body("evento.descricao", equalTo("Esse é o melhor evento do mundo, o grande evento...")).
					statusCode(equalTo(200));
	}

	@Test
	@Override
	public void deveRetornarOkAoBuscarLista() {
		get(PAPER_CONTEXT).
			then().assertThat().
				body(notNullValue()).
				statusCode(equalTo(200));
	}
	
	@Test
	@Override
	public void deveRetornar201AoCadastrar() { /* TODO: IMPLEMENTAR */ }

	@Test
	@Override
	public void deveRetornarOkAoApagar() { /* TODO: IMPLEMENTAR */ }

	@Test
	@Override
	public void deveRetornarOkAoAtualizar() { /* TODO: IMPLEMENTAR */ }
	
	@Test
	@Override
	public void deveRetornarNotFoundAoBuscarPorId() {
		get(PAPER_CONTEXT + "/" + ID_MAX).
			then().assertThat().
				body( equalTo( getMessage404(ID_MAX) ) ).
				statusCode( equalTo( 404 ) );
	}
	
	@Test
	@Override
	public void deveRetornarNotFoundAoApagar() { /* TODO: IMPLEMENTAR */ }

	@Test
	@Override
	public void deveRetornarNotFoundAoAtualizar() { /* TODO IMPLEMENTAR */ }
	
	@Test
	@Override
	public void deveRetornarUnauthorizedAoCadastrar() {
		given().
			contentType(JSON).
			body(jsonPaper).
		when().
			post(PAPER_CONTEXT).
		then().
			statusCode(equalTo(401));
	}

	@Test
	@Override
	public void deveRetornarStatusUnauthorizedAoApagar() {
		given().
			contentType(JSON).
			body(jsonPaper).
		when().
			delete(PAPER_CONTEXT + "/7").
		then().
			statusCode(equalTo(401));
	}

	@Test
	@Override
	public void deveRetornarStatusUnauthorizedAoAtualizar() {
		given().
			contentType(JSON).
			body(jsonPaper).
		when().
			put(PAPER_CONTEXT + "/7").
		then().assertThat().
			statusCode(equalTo(401));
	}

}
