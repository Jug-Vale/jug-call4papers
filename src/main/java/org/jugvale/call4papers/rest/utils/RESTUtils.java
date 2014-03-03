package org.jugvale.call4papers.rest.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class RESTUtils {

	public static <T> T lanca404SeNulo(T o, String message){
		if(o == null){
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(message).build());
		}
		return o;
	}
}
