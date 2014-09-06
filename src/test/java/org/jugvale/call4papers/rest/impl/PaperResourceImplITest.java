package org.jugvale.call4papers.rest.impl;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.jugvale.call4papers.rest.util.Constantes.SERVICES_CONTEXT;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.http.ContentType.*;

public class PaperResourceImplITest {

	private static final String PAPER_CONTEXT = SERVICES_CONTEXT
			.concat("/paper");

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
	public void deveRetornarPaperEmBuscaPorId() throws Exception {

		get(PAPER_CONTEXT + "/7").
			then().assertThat().
					body(notNullValue()).
					body("autores.nome", containsInAnyOrder("Maria", "Josevaldo")).
					body("evento.descricao", equalTo("Esse é o melhor evento do mundo, o grande evento...")).
					statusCode(equalTo(200));
	}

	@Test
	public void deveListarTodosPapersERetornarStatusOK() throws Exception {
		get(PAPER_CONTEXT).then().body(notNullValue()).statusCode(equalTo(200));
	}

	@Test
	public void deveRetornarUnauthorizedParaCriarNovoPaper() {
		given().contentType(JSON).body(jsonPaper).when().post(PAPER_CONTEXT).then().statusCode(equalTo(401));
	}

	@Test
	public void deveRetornarUnauthorizedParaApagarPorId() {
		given().contentType(JSON).body(jsonPaper).when().delete(PAPER_CONTEXT + "/7").then().statusCode(equalTo(401));
	}

	@Test
	public void deveRetornarUnauthorizedParaAtualizar() {
		given().contentType(JSON).body(jsonPaper).when().put(PAPER_CONTEXT + "/7").then().statusCode(equalTo(401));
	}

}
