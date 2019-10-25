package org.jugvale.cfp.rest;

import static io.restassured.RestAssured.given;

import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.jugvale.cfp.model.Autor;
import org.jugvale.cfp.model.Evento;
import org.jugvale.cfp.model.Paper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class AutorResourceTest extends BaseTest {

	private static final String URI_AUTOR = "/autor";
	private static final String URI_AUTOR_PARAM = "/autor/{id}";
	private String autorJson;
	
	@BeforeEach
	@Transactional
	public void limpaAutoresECria() {
		
		Paper.deleteAll();
    	Autor.deleteAll();
    	Evento.deleteAll();
				
		Autor autor = new Autor();
		autor.email = "antonio@email.com";
		autor.nome = "Antônio";
		autor.miniCurriculo = "Java programmer for a long, long, long, long time";
		autor.site = "http://www.antonio.com";
		autor.telefone = "+55 11 123456789";
		
		autorJson = JsonbBuilder.create().toJson(autor);
	}
	
	@Test
	public void deveRetornarStatus401ParaListarTodos( ) {
		given().get(URI_AUTOR).then().statusCode(401);
	}
	
	@Test
	public void deveRetornarStatus401ParaApagarPorId( ) {
		given().delete(URI_AUTOR_PARAM, Long.MAX_VALUE).then().statusCode(401);
	}
	
	@Test
	public void deveRetornarStatus401ParaBuscarPorId( ) {
		given().get(URI_AUTOR_PARAM, Long.MAX_VALUE).then().statusCode(401);
	}
	
	@Test
	public void deveRetornarStatus401ParaAtualizar( ) {
		
		given().contentType(ContentType.JSON).body(autorJson)
					   						 .put(URI_AUTOR_PARAM, Long.MAX_VALUE)
					   						 .then()
					   						 .statusCode(401);
	}
	
	@Test
	public void deveCriarAutorERetornarStatus201() {
		given().body(autorJson)
			   .contentType(ContentType.JSON)
			   .post(URI_AUTOR).then()
			   .statusCode(201)
			   .content(Matchers.notNullValue());
	}
	
	@Test
	public void deveBuscarAutorPorIdERetornarStatus200() {
		
		Long id = given().body(autorJson)
				 .contentType(ContentType.JSON)
				 .post(URI_AUTOR).then()
				 .statusCode(201).extract()
				 .as(Long.class);

		givenWithAuth().get(URI_AUTOR_PARAM, id).then().statusCode(200).content(Matchers.notNullValue());
	}
	
	@Test
	public void deveBuscarAutorPorIdERetornarStatus404() {
		givenWithAuth().get(URI_AUTOR_PARAM, Long.MAX_VALUE)
					   .then().statusCode(404)
					   .content(Matchers.containsString("Não encontrado"));
	}
	
	@Test
	public void deveAtualizarAutorERetornarStatus200() {
		
		Long id = given().body(autorJson)
				 .contentType(ContentType.JSON)
				 .post(URI_AUTOR).then()
				 .statusCode(201).extract()
				 .as(Long.class);
		
		String EMAIL_NOVO = "antonio2@email.com";
		
		Autor autor = JsonbBuilder.create().fromJson(autorJson, Autor.class);
		autor.email = EMAIL_NOVO;
		
		givenWithAuth().contentType(ContentType.JSON)
					   .body(JsonbBuilder.create().toJson(autor))
					   .put(URI_AUTOR_PARAM, id)
					   .then().statusCode(200)
					   .body("email", Matchers.containsString(EMAIL_NOVO))
					   .body("id", Matchers.equalTo(id.intValue()));
	}
	
	@Test
	public void deveDeletarAutorCriadoERetornarStatus204() {
		
		Long id = given().body(autorJson)
				 .contentType(ContentType.JSON)
				 .post(URI_AUTOR).then()
				 .statusCode(201).extract()
				 .as(Long.class);
		
		givenWithAuth().delete(URI_AUTOR_PARAM, id).then().statusCode(204);
		givenWithAuth().get(URI_AUTOR_PARAM, id).then().statusCode(404);
		
	}
}