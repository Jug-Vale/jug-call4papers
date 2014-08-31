package org.jugvale.call4papers.rest.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

public class RESTUtils {

	public static <T> T lanca404SeNulo(T o, String message) {
		if (o == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.entity(message).build());
		}
		return o;
	}

	public static <T> T lanca404SeNulo(T o, long id) {
		return lanca404SeNulo(o, "ID: '" + id + "' não encontrado");
	}

	public static Response recursoCriado(Class<?> t, long id) {
		return Response.created(
				UriBuilder.fromResource(t).path(String.valueOf(id)).build())
				.build();
	}
}
