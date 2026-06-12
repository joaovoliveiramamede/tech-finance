package com.techfinance.pessoal.desktop.navigation;

import com.techfinance.pessoal.desktop.DesktopApplication;

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
            URL url = getClass().getResource(
                "/com/techfinance/pessoal/desktop/fxml/" + fxml
            );

            FXMLLoader loader = new FXMLLoader(url);
            loader.setControllerFactory(
                DesktopApplication.getInjector()::getInstance
            );
            
            Node view = loader.load();

            contentArea.getChildren().setAll(view);

        } catch (IOException exception) {
            throw new RuntimeException("Erro ao carregar página: " + fxml, exception);
        }
    }
}
