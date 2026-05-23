package com.techfinance.pessoal.desktop.controller;

import com.techfinance.pessoal.desktop.navigation.AppNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (isBlank(name)) {
            showError("Informe seu nome.");
            return;
        }

        if (name.trim().length() < 3) {
            showError("O nome deve ter pelo menos 3 caracteres.");
            return;
        }

        if (isBlank(email)) {
            showError("Informe seu e-mail.");
            return;
        }

        if (!email.contains("@")) {
            showError("Informe um e-mail válido.");
            return;
        }

        if (isBlank(password)) {
            showError("Informe uma senha.");
            return;
        }

        if (password.length() < 6) {
            showError("A senha deve ter pelo menos 6 caracteres.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("As senhas não conferem.");
            return;
        }

        AppNavigator.navigateToMain();
    }

    @FXML
    private void goToLogin() {
        AppNavigator.navigateToLogin();
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