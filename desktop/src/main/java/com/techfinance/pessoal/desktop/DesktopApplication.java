package com.techfinance.pessoal.desktop;

import com.techfinance.pessoal.desktop.navigation.AppNavigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class DesktopApplication extends Application {

    @Override
    public void start(Stage stage) {
        AppNavigator.init(stage);
        AppNavigator.navigateToLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}