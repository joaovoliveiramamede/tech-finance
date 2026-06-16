package com.techfinance.pessoal.desktop.navigation;

import com.techfinance.pessoal.desktop.DesktopApplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.net.URL;

public class SceneNavigator {

    private final StackPane contentArea;
    private Object currentController;

    public SceneNavigator(StackPane contentArea) {
        this.contentArea = contentArea;
    }

    public void navigateTo(String fxml) {
        try {
            URL fxmlUrl = getClass().getResource(
                    "/com/techfinance/pessoal/desktop/fxml/" + fxml
            );

            if (fxmlUrl == null) {
                throw new IllegalStateException("FXML interno não encontrado: " + fxml);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setControllerFactory(
                    DesktopApplication.getInjector()::getInstance
            );

            Parent root = loader.load();
            currentController = loader.getController();

            contentArea.getChildren().setAll(root);

        } catch (Exception exception) {
            throw new RuntimeException("Erro ao carregar tela interna: " + fxml, exception);
        }
    }

    public void refreshCurrent() {
        if (currentController instanceof Refreshable refreshable) {
            refreshable.refresh();
        }
    }
}