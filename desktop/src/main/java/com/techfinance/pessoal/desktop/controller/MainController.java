package com.techfinance.pessoal.desktop.controller;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.navigation.FxmlViewLoader;
import com.techfinance.pessoal.desktop.navigation.Navigator;
import com.techfinance.pessoal.desktop.navigation.SceneNavigator;
import com.techfinance.pessoal.desktop.service.AuthService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainController {

    private static final String ACTIVE_CLASS = "sidebar-button-active";

    private final AuthService authService;
    private final Navigator appNavigator;
    private final FxmlViewLoader viewLoader;

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
    public MainController(
            AuthService authService,
            Navigator appNavigator,
            FxmlViewLoader viewLoader) {

        this.authService = authService;
        this.appNavigator = appNavigator;
        this.viewLoader = viewLoader;
    }

    @FXML
    private void initialize() {
        navigator = new SceneNavigator(contentArea, viewLoader);

        userNameLabel.setText(
                authService.getName() != null
                        ? authService.getName()
                        : authService.getUsername()
        );

        showHome();
    }

    @FXML
    public void showHome() {
        navigate("home.fxml", homeButton);
    }

    @FXML
    public void showAccounts() {
        navigate("accounts.fxml", accountsButton);
    }

    @FXML
    public void showTransactions() {
        navigate("transactions.fxml", transactionsButton);
    }

    @FXML
    public void showCategories() {
        navigate("categories.fxml", categoriesButton);
    }

    public void refreshCurrentPage() {
        if (navigator != null) {
            navigator.refreshCurrent();
        }
    }

    @FXML
    private void logout() {
        authService.logout();
        appNavigator.toLogin();
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
        if (button != null) {
            button.getStyleClass().remove(ACTIVE_CLASS);
        }
    }
}
