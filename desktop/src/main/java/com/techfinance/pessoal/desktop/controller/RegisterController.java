package com.techfinance.pessoal.desktop.controller;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.dto.request.RegisterRequest;
import com.techfinance.pessoal.desktop.navigation.AppNavigator;
import com.techfinance.pessoal.desktop.service.AuthService;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {

    private final AuthService authService;

    @Inject
    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleRegister() {

        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isBlank(name)) {
            showError("Informe seu nome.");
            return;
        }

        if (name.length() < 3) {
            showError("Nome inválido.");
            return;
        }

        if (isBlank(username)) {
            showError("Informe username.");
            return;
        }

        if (password.length() < 6) {
            showError("Senha inválida.");
            return;
        }

        try {

            authService.register(
                    new RegisterRequest(
                            name,
                            username,
                            password,
                            "USER"
                    )
            );

            AppNavigator.navigateToLogin();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void goToLogin() {
        AppNavigator.navigateToLogin();
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}