package com.techfinance.pessoal.desktop.controller;

import com.techfinance.pessoal.desktop.navigation.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane contentArea;

    private SceneNavigator navigator;

    @FXML
    public void initialize() {
        navigator = new SceneNavigator(contentArea);

        // Página inicial
        navigator.navigateTo("dashboard.fxml");
    }

    @FXML
    private void openDashboard() {
        navigator.navigateTo("dashboard.fxml");
    }

    @FXML
    private void openWallet() {
        navigator.navigateTo("wallet.fxml");
    }

    @FXML
    private void openTransactions() {
        navigator.navigateTo("transactions.fxml");
    }

    @FXML
    private void openInvestments() {
        navigator.navigateTo("investments.fxml");
    }

    @FXML
    private void openProfile() {
        navigator.navigateTo("profile.fxml");
    }
}