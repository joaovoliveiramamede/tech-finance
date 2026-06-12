package com.techfinance.pessoal.desktop.navigation;

import com.techfinance.pessoal.desktop.DesktopApplication;
import com.techfinance.pessoal.desktop.service.AuthService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class AppNavigator {

    private static Stage primaryStage;

    private AppNavigator() {}

    public static void init(Stage stage) {

        primaryStage = stage;
        primaryStage.setTitle("TechFinance");

        primaryStage.setMaximized(true);

        primaryStage.centerOnScreen();
    }

    public static void navigateToRegister() {
        load("register.fxml", "register.css");
    }

    public static void navigateToHome() {
        AuthService authService = DesktopApplication
                .getInjector()
                .getInstance(AuthService.class);

        if (!authService.isAuthenticated()) {
            navigateToLogin();
            return;
        }

        load("main.fxml", "app.css");
    }

    public static void navigateToLogin() {
        load("login.fxml", "login.css");
    }

    private static void load(String fxml, String css) {

        try {

            URL url = AppNavigator.class.getResource(
                    "/com/techfinance/pessoal/desktop/fxml/" + fxml
            );

            FXMLLoader loader = new FXMLLoader(url);

            loader.setControllerFactory(
                    DesktopApplication.getInjector()::getInstance
            );

            Parent root = loader.load();

            Scene scene = new Scene(
                    root,
                    Screen.getPrimary().getBounds().getWidth(),
                    Screen.getPrimary().getBounds().getHeight()
            );

            URL cssUrl = AppNavigator.class.getResource(
                    "/com/techfinance/pessoal/desktop/css/" + css
            );

            scene.getStylesheets().add(cssUrl.toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
