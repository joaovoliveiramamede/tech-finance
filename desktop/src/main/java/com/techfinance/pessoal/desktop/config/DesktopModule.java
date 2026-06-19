package com.techfinance.pessoal.desktop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.techfinance.pessoal.desktop.navigation.JavaFxNavigator;
import com.techfinance.pessoal.desktop.navigation.Navigator;
import com.techfinance.pessoal.desktop.service.AuthService;

import javafx.stage.Stage;
import okhttp3.OkHttpClient;

public class DesktopModule extends AbstractModule {

    private final Stage primaryStage;

    public DesktopModule(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    protected void configure() {
        bind(Stage.class).toInstance(primaryStage);
        bind(OkHttpClient.class).toInstance(new OkHttpClient());
        bind(ObjectMapper.class).toInstance(createObjectMapper());
        bind(AuthService.class);
        bind(Navigator.class).to(JavaFxNavigator.class);
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
