package org.jugvale.cfp.rest;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

public class TestFilter implements ContainerRequestFilter {
    
    @Context SecurityContext sec;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        
    }

}
