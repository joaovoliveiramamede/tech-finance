package com.techfinance.pessoal.desktop.navigation;

import com.techfinance.pessoal.desktop.DesktopApplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.net.URL;

public class SceneNavigator {

    private final StackPane contentArea;

    public SceneNavigator(StackPane contentArea) {
        this.contentArea = contentArea;
    }

    public void navigateTo(String fxml) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/techfinance/pessoal/desktop/fxml/" + fxml));

            loader.setControllerFactory(
                    DesktopApplication.getInjector()::getInstance);

            Parent root = loader.load();

            String cssFile = fxml.replace(".fxml", ".css");

            URL cssUrl = getClass().getResource(
                    "/com/techfinance/pessoal/desktop/css/" + cssFile);

            if (cssUrl != null) {

                root.setStyle(
                        "-fx-font-family: 'Segoe UI';");

            }

            contentArea.getChildren().setAll(root);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}