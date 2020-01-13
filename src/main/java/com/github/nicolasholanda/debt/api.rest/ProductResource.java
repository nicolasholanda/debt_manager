package com.github.nicolasholanda.debt.api.rest;

import com.github.nicolasholanda.debt.model.Product;
import com.github.nicolasholanda.debt.service.BrandService;
import com.github.nicolasholanda.debt.service.ProductService;

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
import static io.vavr.API.Option;
import static java.lang.Integer.valueOf;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.*;

@Path("/produtos")
@RequestScoped
@Produces("application/json;charset=UTF-8")
public class ProductResource {

    @Context
    UriInfo uriInfo;

    @Inject
    private ProductService service;

    @Inject
    private BrandService brandService;


    @GET
    public Response findAllPaginated(@QueryParam("filtro") @DefaultValue("") String filter,
                                          @QueryParam("ordem") @DefaultValue("name") String order,
                                          @QueryParam("inicio") @DefaultValue("0") @Min(0) Integer offset,
                                          @QueryParam("limite") @DefaultValue("9999") @Min(1) Integer limit,
                                          @QueryParam("min_preco") @DefaultValue("0") @Min(0) float minPrice,
                                          @QueryParam("max_preco") @DefaultValue("9999") float maxPrice,
                                          @QueryParam("marcas_ids") @DefaultValue("") String brandIds,
                                          @QueryParam("available") @DefaultValue("true") boolean available) {
        var listOfBrands = Option(brandIds).filter(b -> !b.isEmpty()).map(b -> List(b.split(",")).map(z -> brandService.findById(valueOf(z))).toJavaList()).getOrNull();
        var finalOrder = validateOrder(order, List("name", "price"));
        var queryFilter = queryFilter(filter, order, offset, limit);
        return buildPaginatedResponse(service.findPaginatedBy(queryFilter, finalOrder, available, listOfBrands, minPrice, maxPrice), queryFilter).build();
    }

    @GET
    @Path("/{id:[0-9]*}")
    public Response findById(@PathParam("id") @Min(1) Integer id) {
        return ok(service.findById(id)).build();
    }

    @POST
    public Response save(@Valid Product product) {
        var savedId = service.saveOrUpdate(product).getId().toString();
        return created(uriInfo.getAbsolutePathBuilder().path(savedId).build()).build();
    }

    @PUT
    @Path("/{id:[0-9]*}")
    public Response update(@PathParam("id") @Min(1) Integer id, @Valid Product product) {
        if(!id.equals(product.getId())) {
            return status(CONFLICT).entity("Não é possível atualizar o produto. As informações enviadas são de um produto diferente.").build();
        }
        service.saveOrUpdate(product);
        return noContent().build();
    }

    @DELETE
    @Path("/{id:[0-9]*}")
    public Response delete(@PathParam("id") @Min(1) Integer id) {
        service.remove(id);
        return ok().build();
    }
}
