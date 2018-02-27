package org.jugvale.call4papers.rest.impl;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.jugvale.call4papers.rest.util.Constantes.ID_MAX;
import static org.jugvale.call4papers.rest.util.Constantes.SERVICES_CONTEXT;
import static org.jugvale.cfp.rest.utils.RESTUtils.getMessage404;

import org.jugvale.call4papers.rest.TestResourceDefault;
import org.jugvale.cfp.model.impl.Autor;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AutorResourceIT implements TestResourceDefault{

	private static final String AUTOR_CONTEXT = SERVICES_CONTEXT.concat("/autor");
	
	private Autor autor;
	private String jsonAutor;

	@Before
	public void setUp() throws Exception {
		 autor = Autor.newAutor()
				.comNome("Maria")
				.comEmail("meuemail@gmail.com")
				.comSite("www.mariajava.com")
				.comTelefone("(99) 9 9999-9999")
				.comMiniCV("Grande conhecida no mundo Java...")
				.build();
		
		 ObjectMapper mapper = new ObjectMapper();
		 jsonAutor = mapper.writeValueAsString(autor);
	}

	@Test
	@Override
	public void deveRetornarOkAoBuscarLista() {
		get(AUTOR_CONTEXT).
			then().assertThat().
				body(notNullValue()).
				body( "usuario.login", containsInAnyOrder("Josevaldo", "Maria") ).
				statusCode(200);
	}

	@Test
	@Override
	public void deveRetornarOkAoBuscarPorId() {
		get(AUTOR_CONTEXT + "/5").
			then().assertThat().
				body(notNullValue()).
				statusCode(200);
	}
	
	@Test
	public void deveRetornarOKAoListarPapersPorAutor() {
		get(AUTOR_CONTEXT + "/5/papers").
			then().assertThat().
				body(notNullValue()).
				statusCode(200);
	}

	@Test
	@Override
	public void deveRetornar201AoCadastrar() { /* TODO: IMPLEMENTAR */  }

	@Test
	@Override
	public void deveRetornarOkAoApagar() { /* TODO: IMPLEMENTAR */  }

	@Test
	@Override
	public void deveRetornarOkAoAtualizar() { }

	@Override
	public void deveRetornarNotFoundAoBuscarPorId() {
		get(AUTOR_CONTEXT + "/" + ID_MAX).
			then().assertThat().
				body( equalTo( getMessage404(ID_MAX) ) ).
				statusCode(404);
	}

	@Test
	public void deveRetornarNotFoundAoListarPapersPorAutor() {
		get(AUTOR_CONTEXT + "/" + ID_MAX + "/papers").
			then().assertThat().
				body( equalTo(getMessage404(ID_MAX)) ).
				statusCode(404);
	}

	@Test
	@Override
	public void deveRetornarNotFoundAoApagar() { /* TODO: IMPLEMENTAR */  }

	@Test
	@Override
	public void deveRetornarNotFoundAoAtualizar() { /* TODO: IMPLEMENTAR */ }

	@Test
	@Override
	public void deveRetornarUnauthorizedAoCadastrar() {
		given().
			contentType(JSON).
			body(jsonAutor).
		when().
			post(AUTOR_CONTEXT).
		then().assertThat().
			statusCode(401);
	}

	@Test
	@Override
	public void deveRetornarStatusUnauthorizedAoAtualizar() {
		given().
			contentType(JSON).
			body(jsonAutor).
		when().
			put(AUTOR_CONTEXT + "/" + ID_MAX).
		then().assertThat().
			statusCode(401);
		
	}

	@Test
	@Override
	public void deveRetornarStatusUnauthorizedAoApagar() {
		given().
			contentType(JSON).
			body(jsonAutor).
		when().
			delete(AUTOR_CONTEXT + "/" + ID_MAX).
		then().assertThat().
			statusCode(401);
	}

	

}
