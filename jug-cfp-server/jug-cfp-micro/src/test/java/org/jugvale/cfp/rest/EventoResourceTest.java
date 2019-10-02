package org.jugvale.cfp.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import javax.json.bind.JsonbBuilder;

import org.jugvale.cfp.model.Evento;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class EventoResourceTest {

    private static final String URI_EVENTO = "/evento";
    private static final String URI_EVENTO_PARAM = URI_EVENTO + "/{id}";
    private static final String URI_EVENTO_PARAM_PAPERS = URI_EVENTO + "/admin/{eventoId}/muda-aceitando-papers";   
    private static final String URI_EVENTO_PARAM_INSCRICOES = URI_EVENTO + "/admin/{eventoId}/muda-inscricoes-abertas";   
    
    @Test
    public void testEventoCRUD() {
        Evento[] eventos = given().get(URI_EVENTO).as(Evento[].class);
        assertEquals(0, eventos.length);
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
        
        Long id = given().body(eJson)
                         .auth().preemptive().basic("admin", "admin")
                         .contentType(ContentType.JSON)
                         .post(URI_EVENTO).then()
                         .statusCode(201)
                         .extract().as(Long.class);
        
        get(URI_EVENTO_PARAM, 123456).then().statusCode(404);
        
        Evento eventoBuscado = get(URI_EVENTO_PARAM, id).then().statusCode(200).extract().as(Evento.class);
        
        assertEquals(evento.nome, eventoBuscado.nome);
        assertFalse(eventoBuscado.aceitandoTrabalhos);
        assertFalse(eventoBuscado.inscricoesAbertas);
        
        
        given().contentType(ContentType.JSON).post(URI_EVENTO_PARAM_PAPERS, id).then().statusCode(401);
        eventoBuscado = given().contentType(ContentType.JSON)
                               .auth().preemptive().basic("admin", "admin")
                               .post(URI_EVENTO_PARAM_PAPERS, id).then()
                               .statusCode(200).extract().as(Evento.class);
        assertTrue(eventoBuscado.aceitandoTrabalhos);
        
        given().contentType(ContentType.JSON).post(URI_EVENTO_PARAM_INSCRICOES, id).then().statusCode(401);
        
        eventoBuscado = given().contentType(ContentType.JSON)
                               .auth().preemptive().basic("admin", "admin")
                               .post(URI_EVENTO_PARAM_INSCRICOES, id)
                               .then().statusCode(200).extract().as(Evento.class);
        assertTrue(eventoBuscado.inscricoesAbertas);
        
    }

}