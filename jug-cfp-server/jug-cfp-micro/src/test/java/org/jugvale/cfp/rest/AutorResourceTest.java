package org.jugvale.cfp.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import javax.json.bind.JsonbBuilder;

import org.jugvale.cfp.model.Autor;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class AutorResourceTest extends BaseTest {
    
    private static final String URI_AUTOR = "/autor";
    private static final String URI_AUTOR_PARAM = "/autor/{id}";

    @Test
    public void autorCRUDTest() {
        Autor autor = new Autor();
        autor.email = "antonio@email.com";
        autor.nome = "Antônio";
        autor.miniCurriculo = "Java programmer for a long, long, long, long time";
        autor.site = "http://www.antonio.com";
        autor.telefone = "+55 11 123456789";
        
        String aJson = JsonbBuilder.create().toJson(autor);
        
        Long id = given().body(aJson)
                         .contentType(ContentType.JSON)
                         .post(URI_AUTOR).then()
                         .statusCode(201)
                         .extract().as(Long.class);
        
        givenWithAuth()
               .get(URI_AUTOR_PARAM, id).then().statusCode(200);
        
        givenWithAuth()
               .get(URI_AUTOR_PARAM, 123l).then().statusCode(404);
        
        
        String EMAIL_NOVO = "antonio2@email.com";
        autor.email = EMAIL_NOVO;
        aJson = JsonbBuilder.create().toJson(autor);
        
        givenWithAuth()
               .contentType(ContentType.JSON)
               .body(aJson).put(URI_AUTOR_PARAM, id).then().statusCode(200);
        
        givenWithAuth()
               .get(URI_AUTOR_PARAM, id).then().statusCode(200)
               .body("email", equalTo(EMAIL_NOVO));
        
        givenWithAuth().delete(URI_AUTOR_PARAM, id).then().statusCode(204);
        
        givenWithAuth().get(URI_AUTOR_PARAM, id).then().statusCode(404);
        
    }

}