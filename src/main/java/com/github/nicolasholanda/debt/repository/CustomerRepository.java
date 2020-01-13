package com.github.nicolasholanda.debt.repository;

import com.github.nicolasholanda.debt.model.Customer;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface CustomerRepository extends EntityRepository<Customer, Integer> {

}
