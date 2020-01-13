package com.github.nicolasholanda.debt.util;

import com.github.nicolasholanda.debt.model.filter.QueryFilter;
import io.vavr.Tuple2;
import io.vavr.collection.List;

import javax.ws.rs.core.Response.ResponseBuilder;

import static io.vavr.API.*;
import static io.vavr.Predicates.isIn;
import static java.lang.String.format;
import static javax.ws.rs.core.Response.ok;
import static org.apache.http.HttpHeaders.CONTENT_RANGE;

public final class ResourceUtils {

    public static <T> ResponseBuilder buildPaginatedResponse(Tuple2<Long, List<T>> tuple, QueryFilter filter) {
        var total = tuple._1;
        var length = (total >= filter.getOffset() + filter.getLimit() ? filter.getOffset() + filter.getLimit() : total) - 1;
        return ok(tuple._2).header(CONTENT_RANGE, tuple._2.isEmpty() ? "0-0/0" : format("%d-%d/%d", filter.getOffset(), length, total));
    }

    public static Tuple2<Boolean, String> validateOrder(String order, List<String> validFields) {
        var finalOrder = order.startsWith("-") ? Tuple(true, order.substring(1)) : Tuple(false, order);
        return Match(finalOrder._2.toLowerCase()).of(
                Case($(isIn(validFields.map(String::toLowerCase).toJavaArray())), finalOrder),
                Case($(), () -> {
                    throw new IllegalArgumentException(format("O campo de ordenação enviado é inválido. Campos disponíveis: %s.", validFields.map(v -> format("\'%s\'", v)).mkString(", ")));
                }));
    }
}
