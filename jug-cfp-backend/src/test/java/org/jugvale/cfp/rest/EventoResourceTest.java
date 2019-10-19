package org.jugvale.cfp.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import javax.json.bind.JsonbBuilder;

import org.jugvale.cfp.model.Evento;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class EventoResourceTest extends BaseTest {

    public static final String URI_EVENTO = "/evento";
    public static final String URI_EVENTO_PARAM = URI_EVENTO + "/{id}";
    public static final String URI_EVENTO_PARAM_PAPERS = URI_EVENTO + "/admin/{eventoId}/muda-aceitando-papers";   
    public static final String URI_EVENTO_PARAM_INSCRICOES = URI_EVENTO + "/admin/{eventoId}/muda-inscricoes-abertas";   
    
    @Test
    public void testEventoCRUD() {
        given().get(URI_EVENTO).then().statusCode(200);
        Evento evento = new Evento();
        evento.dataFim = new Date();
        evento.dataInicio = new Date();
        evento.descricao = "EVENTO TESTE";
        evento.local = "Rua da Alegria";
        evento.nome = "Super evento";
        evento.url = "http://jugvale.com";
        
        String eJson = JsonbBuilder.create().toJson(evento);
        
        given().body(eJson)
               .contentType(ContentType.JSON)
               .post(URI_EVENTO).then()
               .statusCode(401);
        
        Long id = givenWithAuth().body(eJson)
                                 .contentType(ContentType.JSON)
                                 .post(URI_EVENTO).then()
                                 .statusCode(201)
                                 .extract().as(Long.class);
        
        get(URI_EVENTO_PARAM, 123456).then().statusCode(404);
        
        get(URI_EVENTO_PARAM, id).then()
                                 .statusCode(200)
                                 .body("nome", equalTo(evento.nome))
                                 .body("aceitandoTrabalhos", equalTo(false))
                                 .body("inscricoesAbertas", equalTo(false));
        
        
        given().contentType(ContentType.JSON).post(URI_EVENTO_PARAM_PAPERS, id).then().statusCode(401);
        
        givenWithAuth().contentType(ContentType.JSON)
                       .post(URI_EVENTO_PARAM_PAPERS, id).then()
                       .statusCode(200).body("aceitandoTrabalhos", equalTo(true));
        
        given().contentType(ContentType.JSON).post(URI_EVENTO_PARAM_INSCRICOES, id).then().statusCode(401);
        
        givenWithAuth().contentType(ContentType.JSON)
                       .post(URI_EVENTO_PARAM_INSCRICOES, id)
                       .then().statusCode(200).body("inscricoesAbertas", equalTo(true));
        
        
        givenWithAuth().delete(URI_EVENTO_PARAM, id).then().statusCode(204);
        get(URI_EVENTO_PARAM, id).then().statusCode(404);
        
    }
    
}