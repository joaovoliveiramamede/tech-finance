package com.techfinance.pessoal.desktop.navigation;

import com.techfinance.pessoal.desktop.DesktopApplication;
import com.techfinance.pessoal.desktop.controller.MainController;
import com.techfinance.pessoal.desktop.service.AuthService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public final class AppNavigator {

    private static Stage primaryStage;
    private static Object currentController;

    private AppNavigator() {}

    public static void init(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("TechFinance");

        var bounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        primaryStage.setMinWidth(1100);
        primaryStage.setMinHeight(700);
        primaryStage.setMaximized(true);
    }

    public static void navigateToRegister() {
        load("register.fxml", "auth.css");
    }

    public static void navigateToLogin() {
        load("login.fxml", "auth.css");
    }

    public static void navigateToCreateAccount() {
        if (!isAuthenticated()) {
            navigateToLogin();
            return;
        }

        load("create-account.fxml", "auth.css");
    }

    public static void navigateToHome() {
        if (!isAuthenticated()) {
            navigateToLogin();
            return;
        }

        load("main.fxml", "app.css");
    }

    public static void refreshHome() {
        if (currentController instanceof MainController mainController) {
            mainController.showHome();
        }
    }

    public static void refreshCurrentPage() {
        if (currentController instanceof MainController mainController) {
            mainController.refreshCurrentPage();
        }
    }

    private static boolean isAuthenticated() {
        AuthService authService = DesktopApplication
                .getInjector()
                .getInstance(AuthService.class);

        return authService.isAuthenticated();
    }

    private static void load(String fxml, String css) {
        try {
            URL fxmlUrl = AppNavigator.class.getResource(
                    "/com/techfinance/pessoal/desktop/fxml/" + fxml
            );

            if (fxmlUrl == null) {
                throw new IllegalStateException("FXML não encontrado: " + fxml);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setControllerFactory(
                    DesktopApplication.getInjector()::getInstance
            );

            Parent root = loader.load();
            currentController = loader.getController();

            Scene scene = primaryStage.getScene();

            if (scene == null) {
                var bounds = Screen.getPrimary().getVisualBounds();
                scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
                primaryStage.setScene(scene);
            } else {
                scene.setRoot(root);
            }

            scene.getStylesheets().clear();

            URL cssUrl = AppNavigator.class.getResource(
                    "/com/techfinance/pessoal/desktop/css/" + css
            );

            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            primaryStage.setMaximized(true);
            primaryStage.show();

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Erro ao carregar tela: " + fxml, exception);
        }
    }
}