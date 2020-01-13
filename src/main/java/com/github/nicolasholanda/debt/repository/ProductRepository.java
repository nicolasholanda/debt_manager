package com.github.nicolasholanda.debt.repository;

import com.github.nicolasholanda.debt.model.Brand;
import com.github.nicolasholanda.debt.model.Product;
import org.apache.deltaspike.data.api.*;

import java.util.List;

@Repository
public interface ProductRepository extends EntityRepository<Product, Integer> {
    @Query(value = "SELECT p FROM Product p " +
                    "WHERE (LOWER(p.name) LIKE LOWER(:filter) OR LOWER(p.description) LIKE LOWER(:filter)) " +
                    "AND (p.price >= :minPrice) " +
                    "AND (p.price <= :maxPrice) " +
                    "AND (p.available = :available) " +
                    "AND ((:brands) IS NULL OR p.brand IN :brands)")
    QueryResult<Product> findPaginatedBy(@QueryParam("filter") String filter,
                                         @QueryParam("available") boolean available,
                                         @QueryParam("minPrice") float minPrice,
                                         @QueryParam("maxPrice") float maxPrice,
                                         @QueryParam("brands") List<Brand> brands);
}
