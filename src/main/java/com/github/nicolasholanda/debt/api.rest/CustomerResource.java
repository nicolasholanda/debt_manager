package com.github.nicolasholanda.debt.api.rest;

import com.github.nicolasholanda.debt.model.Customer;
import com.github.nicolasholanda.debt.service.CustomerService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.*;

@Path("/clientes")
@RequestScoped
@Produces("application/json;charset=UTF-8")
public class CustomerResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private CustomerService service;

    @GET
    @Path("/{id:[0-9]*}")
    public Response findById(@PathParam("id") @Min(1) Integer id) {
        return ok(service.findById(id)).build();
    }

    @POST
    public Response save(@Valid Customer customer) {
        var savedId = service.saveOrUpdate(customer).getId().toString();
        return created(uriInfo.getAbsolutePathBuilder().path(savedId).build()).build();
    }

    @PUT
    @Path("/{id:[0-9]*}")
    public Response update(@PathParam("id") @Min(1) Integer id, @Valid Customer customer) {
        if(!id.equals(customer.getId())) {
            return status(CONFLICT).entity("Não é possível atualizar o cliente. As informações enviadas são de um cliente diferente.").build();
        }
        service.saveOrUpdate(customer);
        return noContent().build();
    }

    @DELETE
    @Path("/{id:[0-9]*}")
    public Response remove(@PathParam("id") @Min(1) Integer id) {
        service.remove(id);
        return ok().build();
    }
}
