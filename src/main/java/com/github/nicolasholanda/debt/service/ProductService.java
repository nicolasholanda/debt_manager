package com.github.nicolasholanda.debt.service;

import com.github.nicolasholanda.debt.model.Product;
import com.github.nicolasholanda.debt.repository.ProductRepository;
import io.vavr.control.Option;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import static java.lang.String.format;

public class ProductService {

    @Inject
    private ProductRepository repository;

    public Product findById(Integer productId) {
        return Option.of(repository.findBy(productId)).getOrElseThrow(() -> {
            throw new NoResultException(format("Nenhum produto com ID %s foi encontrado.", productId));
        });
    }

    public Product saveOrUpdate(Product product) {
        return repository.save(product);
    }
}
