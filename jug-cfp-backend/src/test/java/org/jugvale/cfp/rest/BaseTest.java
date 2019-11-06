package org.jugvale.cfp.rest;

import static io.restassured.RestAssured.given;

import io.restassured.specification.RequestSpecification;

public class BaseTest {
	
    private final static String ADMIN = "admin";
    private final static String PASSWORD = "admin";
    
    public static RequestSpecification givenWithAuth() {
        return given().auth().preemptive().basic(ADMIN, PASSWORD);
    }
    
}