package com.github.nicolasholanda.debt.repository;

import com.github.nicolasholanda.debt.model.Customer;
import org.apache.deltaspike.data.api.*;

@Repository
public interface CustomerRepository extends EntityRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c " +
            "WHERE LOWER(c.name) LIKE :filter OR " +
            "LOWER(c.email) LIKE :filter OR " +
            "LOWER(c.phone_number) LIKE :filter")
    QueryResult<Customer> findPaginatedBy(@QueryParam("filter") String filter);
}
