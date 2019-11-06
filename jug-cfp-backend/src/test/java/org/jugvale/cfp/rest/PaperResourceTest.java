package org.jugvale.cfp.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;

import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.jugvale.cfp.model.Autor;
import org.jugvale.cfp.model.Evento;
import org.jugvale.cfp.model.Paper;
import org.jugvale.cfp.model.Tipo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class PaperResourceTest extends BaseTest {

    private static final String URI_PAPER = "/paper";
    private static final String URI_PAPER_PARAM = URI_PAPER + "/{id}";
    private static final String URI_PAPER_VOTO = URI_PAPER_PARAM + "/votar";
	private String paperJson;
    
    @BeforeEach
	@Transactional
	public void limpaDadosECria() {
    	
    	limpa();
    	
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
        
        long eventoId = givenWithAuth().body(JsonbBuilder.create().toJson(evento))
                					   .contentType(ContentType.JSON)
                					   .post(EventoResourceTest.URI_EVENTO)
                					   .then().extract()
                					   .as(Long.class);
        
		evento.id = eventoId;
		
		Paper paper = new Paper();
		
		paper.evento = evento;
		paper.descricao = "Super talk for super guys with a super description that makes everything more super!";
		paper.titulo = "Super talk";
		paper.autores.add(autor);
		paper.tipo = Tipo.PALESTRA;
		paper.dataSubmissao = new Date();
		
		paperJson = JsonbBuilder.create().toJson(paper);
    	
    }

    @AfterEach
    @Transactional
    public void limpa() {
        Paper.deleteAll();
    	Autor.deleteAll();
    	Evento.deleteAll();
    }
    
    @Test
    public void deveRetornarStatus201AoCriarPaper() {
    	given().body(paperJson).contentType(ContentType.JSON)
							           .post(URI_PAPER)
							           .then().statusCode(201);
    }
    
    @Test
    public void deveBuscarPaperIdERetornar200() {
    	
    	long paperId = givenWithAuth().body(paperJson)
                					  .contentType(ContentType.JSON)
                					  .post(URI_PAPER)
                					  .then().statusCode(201)
                					  .extract().as(Long.class);

    	get(URI_PAPER_PARAM, paperId).then().statusCode(200);
    	
    }

    @Test
    public void deveRetornar404EmBuscaPorPaperId() {
    	get(URI_PAPER_PARAM, Long.MAX_VALUE).then().statusCode(404);
    }
    
    @Test
    public void deveRetornar401ParaAtualizarPaper() {
    	
    	given().contentType(ContentType.JSON)
				       .body(paperJson)
				       .put(URI_PAPER_PARAM, Long.MAX_VALUE)
				       .then().statusCode(401);
    }
    
    @Test
    public void deveRetornar200ParaAtualizarPaper() {
    	
    	long paperId = givenWithAuth().body(paperJson)
									  .contentType(ContentType.JSON)
									  .post(URI_PAPER)
									  .then().statusCode(201)
									  .extract().as(Long.class);
    	
    	Paper paper = JsonbBuilder.create().fromJson(paperJson, Paper.class);
    	
    	String tituloNovo = "Titulo novo";
        String descricaoNova = paper.descricao + "nova";
        Tipo novoTipo = Tipo.MINI_CURSO;
        
        paper.titulo = tituloNovo;
        paper.descricao = descricaoNova;
        paper.tipo = novoTipo;
        paper.aceito = true;
        
        givenWithAuth().contentType(ContentType.JSON)
                       .body(JsonbBuilder.create().toJson(paper))
                       .put(URI_PAPER_PARAM, paperId)
                       .then().statusCode(200)
                       .body("titulo", equalTo(tituloNovo))
                       .body("aceito", equalTo(true))
                       .body("descricao", equalTo(descricaoNova))
                       .body("tipo", equalTo(novoTipo.name()));
    }
    
    @Test
    public void validaVoto() {
    	
    	long paperId = givenWithAuth().body(paperJson)
									  .contentType(ContentType.JSON)
									  .post(URI_PAPER)
									  .then().statusCode(201)
									  .extract().as(Long.class);
    	
    	given().contentType(ContentType.JSON).post(URI_PAPER_VOTO, paperId)
			           						 .then().statusCode(200)
			           						 .body("nota", Matchers.equalTo(1));
    	
    	given().contentType(ContentType.JSON).post(URI_PAPER_VOTO, paperId)
			        						 .then().statusCode(200)
			        						 .body("nota", Matchers.equalTo(2));
    }
    
    @Test
    public void deveDeletarPaperERetornar204() {
    	long paperId = givenWithAuth().body(paperJson)
									  .contentType(ContentType.JSON)
									  .post(URI_PAPER)
									  .then().statusCode(201)
									  .extract().as(Long.class);

    	givenWithAuth().delete(URI_PAPER_PARAM, paperId).then().statusCode(204);
    	get(URI_PAPER_PARAM, paperId).then().statusCode(404);
    }
    
    @Test
    public void deveDeletarPaperERetornar401() {
    	
    	long paperId = givenWithAuth().body(paperJson)
									  .contentType(ContentType.JSON)
									  .post(URI_PAPER)
									  .then().statusCode(201)
									  .extract().as(Long.class);

    	given().delete(URI_PAPER_PARAM, paperId).then().statusCode(401);
    	get(URI_PAPER_PARAM, paperId).then().statusCode(200);
    }
    
}
