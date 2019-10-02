package org.jugvale.cfp.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

public class RESTUtilsTest {
    
    @Test
    public void checkNullableEntitiesAndRemapTest() {
        Response response = RESTUtils.checkNullableEntitiesAndRemap(1l, null, (a, b) -> "");
        assertEquals(404, response.getStatus());
        response = RESTUtils.checkNullableEntitiesAndRemap(null, "", (a, b) -> "");
        assertEquals(404, response.getStatus());
        response = RESTUtils.checkNullableEntitiesAndRemap("", "", (a, b) -> "ENTITY");
        assertEquals(200, response.getStatus());
        assertEquals("ENTITY", response.getEntity());
    }

}
