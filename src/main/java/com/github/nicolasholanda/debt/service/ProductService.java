package com.github.nicolasholanda.debt.service;

import com.github.nicolasholanda.debt.model.Brand;
import com.github.nicolasholanda.debt.model.Product;
import com.github.nicolasholanda.debt.model.filter.QueryFilter;
import com.github.nicolasholanda.debt.repository.ProductRepository;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import io.vavr.collection.List;

import static io.vavr.API.*;
import static io.vavr.collection.List.ofAll;
import static java.lang.String.format;

public class ProductService {

    @Inject
    private ProductRepository repository;

    public Product findById(Integer productId) {
        return Option.of(repository.findBy(productId)).getOrElseThrow(() -> {
            throw new NoResultException(format("Nenhum produto com ID %s foi encontrado.", productId));
        });
    }

    @Transactional
    public Product saveOrUpdate(Product product) {
        return repository.save(product);
    }

    @Transactional
    public void remove(Integer productId) {
        repository.remove(findById(productId));
    }

    public Tuple2<Long, List<Product>> findPaginatedBy(QueryFilter filter, Tuple2<Boolean, String> order, boolean available, java.util.List<Brand> brands, float minPrice, float maxPrice) {
        var result = repository.findPaginatedBy(filter.getFilter(), available, minPrice, maxPrice, brands);
        result = order._1 ? result.orderDesc("p." + order._2, false) : result.orderAsc("p." + order._2, false);
        var finalResult = ofAll(result.firstResult(filter.getOffset()).maxResults(filter.getLimit()).getResultList());
        return Tuple(result.count(), finalResult);
    }
}
