package com.techfinance.pessoal.desktop.controller;

import com.techfinance.pessoal.desktop.navigation.AppNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (isBlank(email)) {
            showError("Informe seu e-mail.");
            return;
        }

        if (!email.contains("@")) {
            showError("Informe um e-mail válido.");
            return;
        }

        if (isBlank(password)) {
            showError("Informe sua senha.");
            return;
        }

        if (password.length() < 6) {
            showError("A senha deve ter pelo menos 6 caracteres.");
            return;
        }

        AppNavigator.navigateToMain();
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