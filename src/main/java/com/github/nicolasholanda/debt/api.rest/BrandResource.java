package com.github.nicolasholanda.debt.api.rest;

import com.github.nicolasholanda.debt.model.Brand;
import com.github.nicolasholanda.debt.service.BrandService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static com.github.nicolasholanda.debt.model.filter.QueryFilter.queryFilter;
import static com.github.nicolasholanda.debt.util.ResourceUtils.buildPaginatedResponse;
import static com.github.nicolasholanda.debt.util.ResourceUtils.validateOrder;
import static io.vavr.API.List;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.*;

@Path("/marcas")
@RequestScoped
@Produces("application/json;charset=UTF-8")
public class BrandResource {

    @Inject
    private BrandService service;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id:[0-9]*}")
    public Response findById(@PathParam("id") @Min(1) Integer id) {
        return ok(service.findById(id)).build();
    }

    @GET
    public Response findAllPaginated(@QueryParam("filtro") @DefaultValue("") String filter,
                                     @QueryParam("ordem") @DefaultValue("name") String order,
                                     @QueryParam("inicio") @DefaultValue("0") @Min(0) Integer offset,
                                     @QueryParam("limite") @DefaultValue("9999") @Min(1) Integer limit) {
        var finalOrder = validateOrder(order, List("name"));
        var queryFilter = queryFilter(filter, order, offset, limit);
        return buildPaginatedResponse(service.findPaginatedBy(queryFilter, finalOrder), queryFilter).build();
    }

    @POST
    public Response save(@Valid Brand brand) {
        var savedId = service.saveOrUpdate(brand).getId().toString();
        return created(uriInfo.getAbsolutePathBuilder().path(savedId).build()).build();
    }

    @PUT
    @Path("/{id:[0-9]*}")
    public Response update(@PathParam("id") @Min(1) Integer id, @Valid Brand brand) {
        if(!id.equals(brand.getId())) {
            return status(CONFLICT).entity("Não é possível atualizar a marca. As informações enviadas são de uma marca diferente.").build();
        }
        service.saveOrUpdate(brand);
        return noContent().build();
    }

    @DELETE
    @Path("/{id:[0-9]*}")
    public Response remove(@PathParam("id") @Min(1) Integer id) {
        service.remove(id);
        return ok().build();
    }

}
