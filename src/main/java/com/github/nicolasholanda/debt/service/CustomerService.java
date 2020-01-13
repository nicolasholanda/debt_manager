package com.github.nicolasholanda.debt.service;

import com.github.nicolasholanda.debt.model.Customer;
import com.github.nicolasholanda.debt.repository.CustomerRepository;
import io.vavr.control.Option;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import static java.lang.String.format;

public class CustomerService {

    @Inject
    private CustomerRepository repository;

    public Customer findById(Integer customerId) {
        return Option.of(repository.findBy(customerId)).getOrElseThrow(() -> {
            throw new NoResultException(format("Nenhum cliente com ID %s foi encontrado.", customerId));
        });
    }

    @Transactional
    public Customer saveOrUpdate(Customer customer) {
        return repository.save(customer);
    }

    @Transactional
    public void remove(Integer customerId) {
        repository.remove(findById(customerId));
    }
}
