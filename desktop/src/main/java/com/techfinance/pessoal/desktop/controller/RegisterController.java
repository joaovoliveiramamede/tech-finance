package com.techfinance.pessoal.desktop.controller;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.dto.request.RegisterRequest;
import com.techfinance.pessoal.desktop.navigation.AppNavigator;
import com.techfinance.pessoal.desktop.service.AuthService;
import com.techfinance.pessoal.desktop.util.FxTasks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
            showError("Informe o usuário.");
            return;
        }

        if (password.length() < 6) {
            showError("Senha inválida. Mínimo 6 caracteres.");
            return;
        }

        FxTasks.run(
            () -> authService.register(
                new RegisterRequest(name, username, password, "USER")
            ),
            AppNavigator::navigateToCreateAccount,
            error -> showError(resolveMessage(error))
        );
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

    private String resolveMessage(Throwable error) {
        if (error.getCause() != null && error.getCause().getMessage() != null) {
            return error.getCause().getMessage();
        }

        return error.getMessage() != null
            ? error.getMessage()
            : "Erro ao registrar usuário.";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
