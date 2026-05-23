package com.techfinance.pessoal.desktop.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class AppNavigator {

    private static Stage primaryStage;

    private AppNavigator() {
    }

    public static void init(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("TechFinance Pessoal");
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(650);
    }

    public static void navigateToLogin() {
        setScene("login.fxml", 1000, 650);
    }

    public static void navigateToRegister() {
        setScene("register.fxml", 1000, 650);
    }

    public static void navigateToMain() {
        setScene("main.fxml", 1200, 720);
    }

    private static void setScene(String fxml, int width, int height) {
        try {
            URL fxmlUrl = AppNavigator.class.getResource(
                    "/com/techfinance/pessoal/desktop/fxml/" + fxml
            );

            if (fxmlUrl == null) {
                throw new IllegalStateException("FXML não encontrado: " + fxml);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load(), width, height);

            addCss(scene, "auth.css");

            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (IOException exception) {
            throw new RuntimeException("Erro ao carregar tela: " + fxml, exception);
        }
    }

    private static void addCss(Scene scene, String cssFile) {
        URL cssUrl = AppNavigator.class.getResource(
                "/com/techfinance/pessoal/desktop/css/" + cssFile
        );

        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
    }
}