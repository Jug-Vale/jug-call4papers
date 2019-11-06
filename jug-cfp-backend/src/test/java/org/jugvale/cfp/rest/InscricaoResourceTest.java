package org.jugvale.cfp.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;

import org.jugvale.cfp.model.Evento;
import org.jugvale.cfp.model.Inscricao;
import org.jugvale.cfp.model.Nivel;
import org.jugvale.cfp.model.Participante;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class InscricaoResourceTest extends BaseTest {
    
    public static final String CRIAR_INSCRICAO_URI = EventoResourceTest.URI_EVENTO_PARAM +  "/inscrever";
    public static final String INSCRICAO_PARAM_URI = "inscricao/{id}";
    public static final String PRESENCA_URI = INSCRICAO_PARAM_URI + "/presenca";
    
    @Inject
    MockMailbox mailbox;

	private String participanteJson;

	private String eventoJson;
    
    @BeforeEach
    @Transactional
    void init() {
        
        limpa();
        
    	mailbox.clear();
    	
        Evento evento = new Evento();
        evento.dataFim = new Date();
        evento.dataInicio = new Date();
        evento.descricao = "EVENTO TESTE";
        evento.local = "Rua da Alegria";
        evento.nome = "Super evento";
        evento.url = "http://jugvale.com";
        
        Participante participante = new Participante();
        participante.email = "antonio@email.com";
        participante.nome = "Antonio";
        participante.instituicao = "FATEC";
        participante.nivel = Nivel.BASICO;
        participante.empresa = "Acme";
        participante.rg = "123456789";
        
        eventoJson = JsonbBuilder.create().toJson(evento);
        participanteJson = JsonbBuilder.create().toJson(participante);
        
    }
    
    @AfterEach
    @Transactional
    public void limpa() {
        Inscricao.deleteAll();
        Participante.deleteAll();
        Evento.deleteAll();
    }

    @Test
    public void deveRealizarAInscNoEventoERetornar200() {
    	
    	Long eventoId = givenWithAuth().body(eventoJson)
                					   .contentType(ContentType.JSON)
                					   .post(EventoResourceTest.URI_EVENTO).then()
                					   .statusCode(201)
                					   .extract().as(Long.class);

    	Inscricao inscricao = given().body(participanteJson)
                					 .contentType(ContentType.JSON)
                					 .post(CRIAR_INSCRICAO_URI, eventoId).then()
                					 .statusCode(200)
                					 .extract().as(Inscricao.class);
    	
    	assertEquals("Super evento", inscricao.evento.nome);
		assertEquals("Antonio", inscricao.participante.nome);
		
    }
    
    @Test
    public void naoDeveRealizarAInscNoMesmoEventoERetornar406() {
    	
    	Long eventoId = givenWithAuth().body(eventoJson)
                					   .contentType(ContentType.JSON)
                					   .post(EventoResourceTest.URI_EVENTO).then()
                					   .statusCode(201)
                					   .extract().as(Long.class);

    	given().body(participanteJson).contentType(ContentType.JSON)
			  						  .post(CRIAR_INSCRICAO_URI, eventoId).then()
			  						  .statusCode(200);
    	
    	given().body(participanteJson).contentType(ContentType.JSON)
				       				  .post(CRIAR_INSCRICAO_URI, eventoId).then()
				       				  .statusCode(409);
    }
    
    @Test
    public void validaEnvioDeEmail() {
    	
    	Long eventoId = givenWithAuth().body(eventoJson)
				   					   .contentType(ContentType.JSON)
				   					   .post(EventoResourceTest.URI_EVENTO).then()
				   					   .statusCode(201)
				   					   .extract().as(Long.class);

    	given().body(participanteJson).contentType(ContentType.JSON)
				 					  .post(CRIAR_INSCRICAO_URI, eventoId).then()
				 					  .statusCode(200);

    	List<Mail> messages = mailbox.getMessagesSentTo("antonio@email.com");
        assertEquals(1, messages.size());
        
        Mail mail = messages.get(0);
        
        assertTrue(mail.getSubject().contains("Super evento"));
        assertTrue(mail.getText().contains("Super evento"));
        assertTrue(mail.getText().contains("Antonio"));
    }
    
    @Test
    public void deveRetornar401AoMarcarPresenca() {
    	given().contentType(ContentType.JSON).post(PRESENCA_URI, Long.MAX_VALUE).then().statusCode(401);
    }
    
    @Test
    public void deveMarcarPresencaERetornar200() {
    	
    	Long eventoId = givenWithAuth().body(eventoJson)
				   					   .contentType(ContentType.JSON)
				   					   .post(EventoResourceTest.URI_EVENTO).then()
				   					   .statusCode(201)
				   					   .extract().as(Long.class);

		Inscricao inscricao = given().body(participanteJson)
						 			 .contentType(ContentType.JSON)
						 			 .post(CRIAR_INSCRICAO_URI, eventoId).then()
						 			 .statusCode(200)
						 			 .extract().as(Inscricao.class);
		
		 givenWithAuth().contentType(ContentType.JSON)
		 				.post(PRESENCA_URI, inscricao.id)
         				.then().statusCode(200)
         				.body("compareceu", equalTo(true));
    }
    
    @Test
    public void deveDesmarcarPresencaERetornar200() {
    	
    	Long eventoId = givenWithAuth().body(eventoJson)
				   					   .contentType(ContentType.JSON)
				   					   .post(EventoResourceTest.URI_EVENTO).then()
				   					   .statusCode(201)
				   					   .extract().as(Long.class);

		Inscricao inscricao = given().body(participanteJson)
						 			 .contentType(ContentType.JSON)
						 			 .post(CRIAR_INSCRICAO_URI, eventoId).then()
						 			 .statusCode(200)
						 			 .extract().as(Inscricao.class);
		
		 givenWithAuth().contentType(ContentType.JSON)
		 				.post(PRESENCA_URI, inscricao.id)
         				.then().statusCode(200)
         				.body("compareceu", equalTo(true));
		 
		 givenWithAuth().contentType(ContentType.JSON)
         				.post(PRESENCA_URI, inscricao.id)
         				.then().statusCode(200)
         				.body("compareceu", equalTo(false));
    }

}