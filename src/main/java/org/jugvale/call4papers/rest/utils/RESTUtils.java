package org.jugvale.call4papers.rest.utils;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jugvale.call4papers.model.DefaultModel;
import org.jugvale.call4papers.util.MessageUtil;

public class RESTUtils {

	public static <T> T lanca404SeNulo(T object, String message) {
		if (object == null) {
			throw new WebApplicationException(Response.status(NOT_FOUND).entity(message).build());
		}
		return object;
	}

	public static <T> T lanca404SeNulo(T object, long id) {
		return lanca404SeNulo(object, getMessage404(id));
	}

	public static <T extends DefaultModel> Response recursoCriado(Class<?> resource, T entity) {
		return Response.created( UriBuilder.fromResource(resource)
								.path(String.valueOf(entity.getId())).build())
								.entity(entity)
								.build();
	}
	
	public static Response recursoCriado(Class<?> resource, Long id) {
		return Response.created( UriBuilder.fromResource(resource)
				.path(String.valueOf(id)).build())
				.build();
	}
	
	public static String getMessage404(long id) {
		String key = RESTUtils.class.getPackage().getName();
		return MessageUtil.getMessage(key + ".status.404", id);
	}
	
}
