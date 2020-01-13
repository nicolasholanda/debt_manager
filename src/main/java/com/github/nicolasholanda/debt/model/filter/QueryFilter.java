package com.github.nicolasholanda.debt.model.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

import static io.vavr.control.Option.of;
import static java.lang.Integer.MAX_VALUE;

public class QueryFilter {

    @JsonProperty("filtro")
    private final String filter;

    @JsonProperty("ordem")
    private String order;

    @JsonProperty("limite")
    private Integer limit;

    @JsonProperty("inicio")
    private Integer offset;

    @JsonCreator
    public QueryFilter(@JsonProperty(value = "ordem") String order,
                       @JsonProperty(value = "filtro") String filter,
                       @JsonProperty(value = "limite") Integer limit,
                       @JsonProperty(value = "inicio") Integer offset) {
        this.order = order;
        this.offset = offset == null ? 0 : offset;
        this.limit = limit == null ? MAX_VALUE : limit;
        this.filter = of(filter).map(i -> "%" + i + "%").getOrElse("%%");
    }

    public QueryFilter(String order, int offset, int limit) {
        this.order = order;
        this.limit = limit;
        this.filter = "%%";
        this.offset = offset;
    }

    public static QueryFilter queryFilter(String filter, String order, int offset, int limit) {
        return new QueryFilter(order, filter, limit, offset);
    }

    public static QueryFilter queryFilter(String filter, String order) {
        return new QueryFilter(order, filter, null, null);
    }

    public static QueryFilter queryFilter(String order, int offset, int limit) {
        return new QueryFilter(order, null, limit, offset);
    }

    public String getOrder() {
        return order;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public String getFilter() {
        return filter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        QueryFilter type = (QueryFilter) o;
        if (order.equals(type.order)) {
            return false;
        }
        if (limit.equals(type.limit)) {
            return false;
        }
        if (offset.equals(type.offset)) {
            return false;
        }
        return filter.equals(type.filter);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + offset.hashCode();
        result = 31 * result + limit.hashCode();
        result = 31 * result + filter.hashCode();
        result = 31 * result + order.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("order = " + order)
                .add("limit = " + limit)
                .add("filter = " + filter)
                .add("offset = " + offset)
                .toString();
    }
}
