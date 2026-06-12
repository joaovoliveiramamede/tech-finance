package com.techfinance.pessoal.desktop.controller;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.navigation.AppNavigator;
import com.techfinance.pessoal.desktop.navigation.SceneNavigator;
import com.techfinance.pessoal.desktop.service.AuthService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainController {

    private static final String ACTIVE_CLASS = "sidebar-button-active";

    private final AuthService authService;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button homeButton;

    @FXML
    private Button accountsButton;

    @FXML
    private Button transactionsButton;

    @FXML
    private Button categoriesButton;

    @FXML
    private Label userNameLabel;

    private SceneNavigator navigator;

    @Inject
    public MainController(AuthService authService) {
        this.authService = authService;
    }

    @FXML
    private void initialize() {
        navigator = new SceneNavigator(contentArea);
        userNameLabel.setText(authService.getUsername());
        showHome();
    }

    @FXML
    private void showHome() {
        navigate("home.fxml", homeButton);
    }

    @FXML
    private void showAccounts() {
        navigate("accounts.fxml", accountsButton);
    }

    @FXML
    private void showTransactions() {
        navigate("transactions.fxml", transactionsButton);
    }

    @FXML
    private void showCategories() {
        navigate("categories.fxml", categoriesButton);
    }

    @FXML
    private void logout() {
        authService.logout();
        AppNavigator.navigateToLogin();
    }

    private void navigate(String fxml, Button activeButton) {
        navigator.navigateTo(fxml);
        setActive(activeButton);
    }

    private void setActive(Button activeButton) {
        clearActive(homeButton);
        clearActive(accountsButton);
        clearActive(transactionsButton);
        clearActive(categoriesButton);

        if (!activeButton.getStyleClass().contains(ACTIVE_CLASS)) {
            activeButton.getStyleClass().add(ACTIVE_CLASS);
        }
    }

    private void clearActive(Button button) {
        button.getStyleClass().remove(ACTIVE_CLASS);
    }
}
