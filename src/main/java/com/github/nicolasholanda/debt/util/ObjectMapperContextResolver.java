package com.github.nicolasholanda.debt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    @Inject
    private ObjectMapper mapper;

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
