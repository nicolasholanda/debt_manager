package com.github.nicolasholanda.debt.service;

import com.github.nicolasholanda.debt.model.Customer;
import com.github.nicolasholanda.debt.model.filter.QueryFilter;
import com.github.nicolasholanda.debt.repository.CustomerRepository;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import static io.vavr.API.Tuple;
import static io.vavr.collection.List.ofAll;
import static java.lang.String.format;

public class CustomerService {

    @Inject
    private CustomerRepository repository;

    public Tuple2<Long, List<Customer>> findPaginatedBy(QueryFilter filter, Tuple2<Boolean, String> order) {
        var result = repository.findPaginatedBy(filter.getFilter());
        result = order._1 ? result.orderDesc("c." + order._2, false) : result.orderAsc("c." + order._2, false);
        var finalResult = ofAll(result.firstResult(filter.getOffset()).maxResults(filter.getLimit()).getResultList());
        return Tuple(result.count(), finalResult);
    }

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
