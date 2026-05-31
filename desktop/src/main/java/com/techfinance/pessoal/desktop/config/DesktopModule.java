package com.techfinance.pessoal.desktop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;

import okhttp3.OkHttpClient;

public class DesktopModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(OkHttpClient.class)
                .toInstance(new OkHttpClient());

        bind(ObjectMapper.class)
                .toInstance(new ObjectMapper());
    }
}