package org.jugvale.cfp.rest;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

public class RESTUtils {

    public static Response created(Class<?> resource, Long id) {
        return Response.created(UriBuilder.fromResource(resource)
                       .path(String.valueOf(id)).build())
                       .entity(id).build();
    }

    public static <T extends PanacheEntity> Response checkEntityAndUpdate(T entity, Consumer<T> propsUpdate) {
        return RESTUtils.checkNullableEntityAndRemap(entity, e -> {
            propsUpdate.accept(e);
            e.persist();
            return e;
        });
    }

    public static <T, U> Response checkNullableEntitiesAndRemap(T entity, U entity2,
                                                                BiFunction<T, U, ?> remapFunction) {
        if (entity != null && entity2 != null) {
            return okWithEntity(remapFunction.apply(entity, entity2));
        } else {
            return notFound();
        }
    }

    public static <T> Response checkNullableEntityAndRemap(T entity, Function<T, T> remapFunction) {
        return Optional.ofNullable(entity)
                       .map(remapFunction)
                       .map(RESTUtils::okWithEntity)
                       .orElseGet(RESTUtils::notFound);
    }

    public static <T> Response checkNullableEntityAndReturn(T entity, Function<T, List<?>> then) {
        return Optional.ofNullable(entity)
                       .map(e -> okWithEntity(then.apply(e)))
                       .orElseGet(RESTUtils::notFound);
    }

    public static Response responseForNullableEntity(Object entity) {
        return Optional.ofNullable(entity)
                       .map(RESTUtils::okWithEntity)
                       .orElseGet(RESTUtils::notFound);
    }

    private static Response okWithEntity(Object entity) {
        return Response.ok(entity).build();
    }

    private static Response notFound() {
        return Response.status(404).entity("Não encontrado").build();
    }

}
