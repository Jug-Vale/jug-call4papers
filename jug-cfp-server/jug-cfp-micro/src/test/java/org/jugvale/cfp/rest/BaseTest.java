package org.jugvale.cfp.rest;

import static io.restassured.RestAssured.given;

import io.restassured.specification.RequestSpecification;

public class BaseTest {
    private final String ADMIN = "admin";
    private final String PASSWORD = "admin";
    
    public RequestSpecification givenWithAuth() {
        return given().auth().preemptive().basic(ADMIN, PASSWORD);
    }
}
