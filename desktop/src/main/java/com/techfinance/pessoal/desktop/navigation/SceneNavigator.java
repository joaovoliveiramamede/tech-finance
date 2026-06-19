package com.techfinance.pessoal.desktop.navigation;

import javafx.scene.layout.StackPane;

public class SceneNavigator {

    private final StackPane contentArea;
    private final FxmlViewLoader viewLoader;
    private Object currentController;

    public SceneNavigator(StackPane contentArea, FxmlViewLoader viewLoader) {
        this.contentArea = contentArea;
        this.viewLoader = viewLoader;
    }

    public void navigateTo(String fxml) {
        FxmlViewLoader.LoadedView view = viewLoader.load(fxml);
        currentController = view.controller();
        contentArea.getChildren().setAll(view.root());
    }

    public void refreshCurrent() {
        if (currentController instanceof Refreshable refreshable) {
            refreshable.refresh();
        }
    }
}
