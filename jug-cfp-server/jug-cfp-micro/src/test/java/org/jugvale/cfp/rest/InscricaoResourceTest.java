package org.jugvale.cfp.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import org.jugvale.cfp.model.Evento;
import org.jugvale.cfp.model.Inscricao;
import org.jugvale.cfp.model.Nivel;
import org.jugvale.cfp.model.Participante;
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
    
    @BeforeEach
    void init() {
        mailbox.clear();
    }
    
    @Test
    public void inscricaoTest() {
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
        
        String eJson = JsonbBuilder.create().toJson(evento);
        
        String pJson = JsonbBuilder.create().toJson(participante);
        
        Long eventoId = givenWithAuth().body(eJson)
                                       .contentType(ContentType.JSON)
                                       .post(EventoResourceTest.URI_EVENTO).then()
                                       .statusCode(201)
                                       .extract().as(Long.class);
                
        Inscricao inscricao = givenWithAuth().body(pJson)
                                             .contentType(ContentType.JSON)
                                             .post(CRIAR_INSCRICAO_URI, eventoId).then()
                                             .statusCode(200)
                                             .extract().as(Inscricao.class);
        
        givenWithAuth().body(pJson)
                       .contentType(ContentType.JSON)
                       .post(CRIAR_INSCRICAO_URI, eventoId).then()
                       .statusCode(409);
        
        assertEquals(evento.nome, inscricao.evento.nome);
        assertEquals(participante.nome, inscricao.participante.nome);
        
        List<Mail> messages = mailbox.getMessagesSentTo(participante.email);
        assertEquals(1, messages.size());
        Mail mail = messages.get(0);
        assertTrue(mail.getSubject().contains(evento.nome));
        assertTrue(mail.getText().contains(evento.nome));
        assertTrue(mail.getText().contains(participante.nome));
        
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