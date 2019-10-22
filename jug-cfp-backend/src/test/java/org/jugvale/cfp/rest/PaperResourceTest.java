package org.jugvale.cfp.rest;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import javax.json.bind.JsonbBuilder;

import org.jugvale.cfp.model.Autor;
import org.jugvale.cfp.model.Evento;
import org.jugvale.cfp.model.Paper;
import org.jugvale.cfp.model.Tipo;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class PaperResourceTest extends BaseTest {

    private static final String URI_PAPER = "/paper";
    private static final String URI_PAPER_PARAM = URI_PAPER + "/{id}";
    private static final String URI_PAPER_VOTO = URI_PAPER_PARAM + "/votar";

    @Test
    public void testPaperCrud() {
    	
        Autor autor = new Autor();
        autor.email = "antonio@email.com";
        autor.nome = "Antônio";
        autor.miniCurriculo = "Java programmer for a long, long, long, long time";
        autor.site = "http://www.antonio.com";
        autor.telefone = "+55 11 123456789";

        Evento evento = new Evento();
        evento.dataFim = new Date();
        evento.dataInicio = new Date();
        evento.descricao = "EVENTO TESTE";
        evento.local = "Rua da Alegria";
        evento.nome = "Super evento";
        evento.url = "http://jugvale.com";
        
        String eJson = JsonbBuilder.create().toJson(evento);
        long eventoId = givenWithAuth().body(eJson)
                                       .contentType(ContentType.JSON)
                                       .post(EventoResourceTest.URI_EVENTO)
                                       .then().extract().as(Long.class);
        evento.id = eventoId;

        Paper paper = new Paper();

        paper.evento = evento;
        paper.descricao = "Super talk for super guys with a super description that makes everything more super!";
        paper.titulo = "Super talk";
        paper.autores.add(autor);
        paper.tipo = Tipo.PALESTRA;
        paper.dataSubmissao = new Date();
        
        String pJson = JsonbBuilder.create().toJson(paper);
        long paperId = givenWithAuth().body(pJson)
                                      .contentType(ContentType.JSON)
                                      .post(URI_PAPER)
                                      .then().statusCode(201)
                                      .extract().as(Long.class);
        
        get(URI_PAPER_PARAM, paperId).then().statusCode(200);
        get(URI_PAPER_PARAM, 123456l).then().statusCode(404);
        
        String tituloNovo = "Titulo novo";
        String descricaoNova = paper.descricao + "nova";
        Tipo novoTipo = Tipo.MINI_CURSO;
        paper.titulo = tituloNovo;
        paper.descricao = descricaoNova;
        paper.tipo = novoTipo;
        paper.aceito = true;
        
        String paperStr = JsonbBuilder.create().toJson(paper);
        givenWithAuth().contentType(ContentType.JSON)
                       .body(paperStr)
                       .put(URI_PAPER_PARAM, paperId)
                       .then().statusCode(200)
                       .body("titulo", equalTo(tituloNovo))
                       .body("aceito", equalTo(true))
                       .body("descricao", equalTo(descricaoNova))
                       .body("tipo", equalTo(novoTipo.name()));
        
        assertTrue(paper.aceito);
        
        long nota = paper.nota;
        paper = givenWithAuth().contentType(ContentType.JSON)
                               .post(URI_PAPER_VOTO, paperId)
                               .then().statusCode(200)
                               .extract().as(Paper.class);
        
        assertEquals(nota + 1, paper.nota);
        
        givenWithAuth().delete(URI_PAPER_PARAM, paperId).then().statusCode(204);
        
        get(URI_PAPER_PARAM, paperId).then().statusCode(404);
        
        givenWithAuth().delete(EventoResourceTest.URI_EVENTO_PARAM, eventoId);
        
    }

}
