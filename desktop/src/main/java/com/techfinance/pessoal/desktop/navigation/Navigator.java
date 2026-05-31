package com.techfinance.pessoal.desktop.navigation;

import java.io.IOException;

import com.techfinance.pessoal.desktop.DesktopApplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigator {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void navigate(Route route) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/techfinance/pessoal/desktop/fxml/" +
                                    route.getFxml()));

            loader.setControllerFactory(
                    DesktopApplication
                            .getInjector()::getInstance);

            Scene scene = new Scene(
                    loader.load(),
                    route.getWidth(),
                    route.getHeight());

            scene.getStylesheets().add(
                    getClass().getResource(
                            "/com/techfinance/pessoal/desktop/css/" +
                                    route.getCss())
                            .toExternalForm());

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}