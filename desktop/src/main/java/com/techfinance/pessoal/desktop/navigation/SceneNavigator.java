package com.techfinance.pessoal.desktop.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class SceneNavigator {

    private final StackPane contentArea;

    public SceneNavigator(StackPane contentArea) {
        this.contentArea = contentArea;
    }

    public void navigateTo(String fxml) {
        try {
            URL fxmlUrl = getClass().getResource(
                    "/com/techfinance/pessoal/desktop/fxml/" + fxml
            );

            if (fxmlUrl == null) {
                throw new IllegalStateException("FXML não encontrado: " + fxml);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);

            Node view = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

        } catch (IOException exception) {
            throw new RuntimeException("Erro ao carregar página: " + fxml, exception);
        }
    }
}