package com.github.nicolasholanda.debt.repository;

import com.github.nicolasholanda.debt.model.Product;
import org.apache.deltaspike.data.api.*;

@Repository
public interface ProductRepository extends EntityRepository<Product, Integer> {
    @Query(value = "SELECT p FROM Product p " +
            "WHERE f.price <= (:maxPrice) " +
            "AND ((:available) IS NULL OR p.available = :available)" +
            "AND (LOWER(unaccent(p.name)) LIKE LOWER(unaccent(:filter)) OR LOWER(unaccent(p.description)) LIKE LOWER(unaccent(:filter)))")
    QueryResult<Product> findPaginatedBy(@QueryParam("available") boolean available,
                                         @QueryParam("filter") String filter,
                                         @QueryParam("maxPrice") float maxPrice);
}
