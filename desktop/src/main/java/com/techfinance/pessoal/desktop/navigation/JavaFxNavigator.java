package com.techfinance.pessoal.desktop.navigation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.techfinance.pessoal.desktop.controller.MainController;
import com.techfinance.pessoal.desktop.service.AuthService;

import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

@Singleton
public class JavaFxNavigator implements Navigator {

    private static final String CSS_BASE_PATH = "/com/techfinance/pessoal/desktop/css/";

    private final Stage primaryStage;
    private final AuthService authService;
    private final FxmlViewLoader viewLoader;

    private Object currentController;

    @Inject
    public JavaFxNavigator(
            Stage primaryStage,
            AuthService authService,
            FxmlViewLoader viewLoader) {

        this.primaryStage = primaryStage;
        this.authService = authService;
        this.viewLoader = viewLoader;

        configureStage();
    }

    @Override
    public void toRegister() {
        load("register.fxml", "auth.css", "register.css");
    }

    @Override
    public void toLogin() {
        load("login.fxml", "auth.css");
    }

    @Override
    public void toCreateAccount() {
        if (!authService.isAuthenticated()) {
            toLogin();
            return;
        }

        load("create-account.fxml", "auth.css", "register.css");
    }

    @Override
    public void toHome() {
        if (!authService.isAuthenticated()) {
            toLogin();
            return;
        }

        load("main.fxml", "app.css");
    }

    @Override
    public void refreshHome() {
        if (currentController instanceof MainController mainController) {
            mainController.showHome();
        }
    }

    @Override
    public void refreshCurrentPage() {
        if (currentController instanceof MainController mainController) {
            mainController.refreshCurrentPage();
        }
    }

    private void configureStage() {
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

    private void load(String fxml, String... stylesheets) {
        FxmlViewLoader.LoadedView view = viewLoader.load(fxml);
        currentController = view.controller();

        Scene scene = primaryStage.getScene();

        if (scene == null) {
            var bounds = Screen.getPrimary().getVisualBounds();
            scene = new Scene(view.root(), bounds.getWidth(), bounds.getHeight());
            primaryStage.setScene(scene);
        } else {
            scene.setRoot(view.root());
        }

        scene.getStylesheets().clear();
        for (String stylesheet : stylesheets) {
            addStylesheet(scene, stylesheet);
        }

        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void addStylesheet(Scene scene, String stylesheet) {
        URL cssUrl = getClass().getResource(CSS_BASE_PATH + stylesheet);

        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
    }
}
