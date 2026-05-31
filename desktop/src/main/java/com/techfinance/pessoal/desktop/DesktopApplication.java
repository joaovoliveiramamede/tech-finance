package com.techfinance.pessoal.desktop;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.techfinance.pessoal.desktop.config.DesktopModule;
import com.techfinance.pessoal.desktop.navigation.AppNavigator;

import javafx.application.Application;
import javafx.stage.Stage;

public class DesktopApplication extends Application {

    private static Injector injector;

    @Override
    public void start(Stage stage) {

        injector = Guice.createInjector(
            new DesktopModule()
        );

        AppNavigator.init(stage);
        AppNavigator.navigateToRegister();
    }

    public static Injector getInjector() {
        return injector;
    }

    public static void main(String[] args) {
        launch(args);
    }
}