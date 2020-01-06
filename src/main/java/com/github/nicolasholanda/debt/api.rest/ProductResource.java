package com.github.nicolasholanda.debt.api.rest;

import com.github.nicolasholanda.debt.model.Product;
import com.github.nicolasholanda.debt.repository.ProductRepository;
import com.github.nicolasholanda.debt.service.ProductService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.ok;

@Path("/produtos")
@RequestScoped
@Produces("application/json;charset=UTF-8")
public class ProductResource {

    @Context
    UriInfo uriInfo;

    @Inject
    private ProductService service;

    @GET
    @Path("/{id:[0-9]*}")
    public Response findById(@PathParam("id") Integer id) {
        return ok(service.findById(id)).build();
    }

    @POST
    public Response save(@Valid Product product) {
        Product saved = service.saveOrUpdate(product);
        return created(uriInfo.getAbsolutePathBuilder().path(saved.getId().toString()).build()).build();
    }
}
