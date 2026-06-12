package com.techfinance.pessoal.desktop.controller;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.dto.request.LoginRequest;
import com.techfinance.pessoal.desktop.navigation.AppNavigator;
import com.techfinance.pessoal.desktop.service.AuthService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private final AuthService authService;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @Inject
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @FXML
    private void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isBlank(username)) {
            showError("Informe seu username.");
            return;
        }

        if (isBlank(password)) {
            showError("Informe sua senha.");
            return;
        }

        try {
            authService.login(new LoginRequest(username, password));
            AppNavigator.navigateToHome();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void goToRegister() {
        AppNavigator.navigateToRegister();
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
