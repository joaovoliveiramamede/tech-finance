package com.techfinance.pessoal.desktop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;

import okhttp3.OkHttpClient;

public class DesktopModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(OkHttpClient.class).toInstance(new OkHttpClient());
        bind(ObjectMapper.class).toInstance(createObjectMapper());
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
