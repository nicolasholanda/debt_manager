package com.github.nicolasholanda.debt.service;

import com.github.nicolasholanda.debt.model.Brand;
import com.github.nicolasholanda.debt.model.filter.QueryFilter;
import com.github.nicolasholanda.debt.repository.BrandRepository;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import static io.vavr.API.Tuple;
import static io.vavr.collection.List.ofAll;
import static java.lang.String.format;

public class BrandService {

    @Inject
    private BrandRepository repository;

    public Brand findById(Integer brandId) {
        return Option.of(repository.findBy(brandId)).getOrElseThrow(() -> {
            throw new NoResultException(format("Nenhuma marca com o ID %s foi encontrada.", brandId));
        });
    }

    @Transactional
    public Brand saveOrUpdate(Brand brand) {
        return repository.save(brand);
    }

    public Tuple2<Long, List<Brand>> findPaginatedBy(QueryFilter filter, Tuple2<Boolean, String> order) {
        var result = repository.findPaginatedBy(filter.getFilter());
        result = order._1 ? result.orderDesc("b." + order._2, false) : result.orderAsc("b." + order._2, false);
        var finalResult = ofAll(result.firstResult(filter.getOffset()).maxResults(filter.getLimit()).getResultList());
        return Tuple(result.count(), finalResult);
    }

    @Transactional
    public void remove(Integer brandId) {
        repository.remove(findById(brandId));
    }
}
