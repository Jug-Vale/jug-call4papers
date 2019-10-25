package org.jugvale.cfp.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;

import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.jugvale.cfp.model.Evento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class EventoResourceTest extends BaseTest {

    public static final String URI_EVENTO = "/evento";
    public static final String URI_EVENTO_PARAM = URI_EVENTO + "/{id}";
    public static final String URI_EVENTO_PARAM_PAPERS = URI_EVENTO + "/admin/{eventoId}/muda-aceitando-papers";   
    public static final String URI_EVENTO_PARAM_INSCRICOES = URI_EVENTO + "/admin/{eventoId}/muda-inscricoes-abertas";
	
    private String eventoJson;   
    
    @BeforeEach
	@Transactional
	public void limpaDadosECria() {
		
		Evento.deleteAll();
				
		Evento evento = new Evento();
		evento.dataFim = new Date();
		evento.dataInicio = new Date();
		evento.descricao = "EVENTO TESTE";
		evento.local = "Rua da Alegria";
		evento.nome = "Super evento";
		evento.url = "http://jugvale.com";

		eventoJson = JsonbBuilder.create().toJson(evento);
	}

    @Test
    public void deveBuscarEventosERetornarStatus200() {
    	given().get(URI_EVENTO).then().statusCode(200);
    }
    
    @Test
    public void deveRetornarStatus401ParaCriarEvento() {
    	given().body(eventoJson)
    		   .contentType(ContentType.JSON)
    		   .post(URI_EVENTO).then()
    		   .statusCode(401);
    }
    
    @Test
    public void deveCriarEventoERetornarStatus201() {
    	givenWithAuth().body(eventoJson)
				       .contentType(ContentType.JSON)
				       .post(URI_EVENTO).then()
				       .statusCode(201)
				       .body(Matchers.notNullValue());
    }
    
    @Test
    public void deveRetornar404ParaBuscaPorId() {
    	get(URI_EVENTO_PARAM, Long.MAX_VALUE).then().statusCode(404);
    }
    
    @Test
    public void deveRetornar200ParaBuscarPorId() {
    	
    	Long id = givenWithAuth().body(eventoJson)
                				 .contentType(ContentType.JSON)
                				 .post(URI_EVENTO).then()
                				 .statusCode(201)
                				 .extract().as(Long.class);

		get(URI_EVENTO_PARAM, id).then()
		                		 .statusCode(200)
		                		 .body("nome", equalTo("Super evento"))
		                		 .body("aceitandoTrabalhos", equalTo(false))
		                		 .body("inscricoesAbertas", equalTo(false));
    	
    }
    
    @Test
    public void deveRetornar401AoMudarAceitandoPapers() {
    	given().contentType(ContentType.JSON).post(URI_EVENTO_PARAM_PAPERS, Long.MAX_VALUE).then().statusCode(401);
    }
    
    @Test
    public void deveRetornar200AoMudarAceitandoPapers() {
    	
    	Long id = givenWithAuth().body(eventoJson)
				 				 .contentType(ContentType.JSON)
				 				 .post(URI_EVENTO).then()
				 				 .statusCode(201)
				 				 .extract().as(Long.class);
    	
    	 givenWithAuth().contentType(ContentType.JSON)
         				.post(URI_EVENTO_PARAM_PAPERS, id).then()
         				.statusCode(200)
         				.body("aceitandoTrabalhos", equalTo(true));
    }
    
    @Test
    public void deveRetornar401AoMudarAceitandoInscricoes() {
    	given().contentType(ContentType.JSON).post(URI_EVENTO_PARAM_INSCRICOES, Long.MAX_VALUE).then().statusCode(401);
    }
    
    @Test
    public void deveRetornar200AoMudarAceitandoInscricoes() {
    	
    	Long id = givenWithAuth().body(eventoJson)
				 				 .contentType(ContentType.JSON)
				 				 .post(URI_EVENTO).then()
				 				 .statusCode(201)
				 				 .extract().as(Long.class);
    	
    	givenWithAuth().contentType(ContentType.JSON)
        			   .post(URI_EVENTO_PARAM_INSCRICOES, id)
        			   .then()
        			   .statusCode(200)
        			   .body("inscricoesAbertas", equalTo(true));
    }
    
    @Test
    public void deveRetornar401DeletarEvento() {
    	given().delete(URI_EVENTO_PARAM, Long.MAX_VALUE).then().statusCode(401);
    }
    
    @Test
    public void deveRetornar204DeletarEvento() {
    	Long id = givenWithAuth().body(eventoJson)
				 				 .contentType(ContentType.JSON)
				 				 .post(URI_EVENTO).then()
				 				 .statusCode(201)
				 				 .extract().as(Long.class);
    	
    	givenWithAuth().delete(URI_EVENTO_PARAM, id).then().statusCode(204);
    	get(URI_EVENTO_PARAM, id).then().statusCode(404);
    }
}