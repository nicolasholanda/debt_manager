package com.github.nicolasholanda.debt.repository;

import com.github.nicolasholanda.debt.model.Brand;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.*;

@Repository
public interface BrandRepository extends EntityRepository<Brand, Integer> {

    @Query("SELECT b FROM Brand b " +
            "WHERE LOWER(b.name) LIKE :filter OR " +
            "LOWER(b.description) LIKE :filter")
    QueryResult<Brand> findPaginatedBy(@QueryParam("filter") String filter);
}
