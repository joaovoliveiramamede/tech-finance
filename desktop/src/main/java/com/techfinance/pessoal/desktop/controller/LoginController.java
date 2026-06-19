package com.techfinance.pessoal.desktop.controller;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.dto.request.LoginRequest;
import com.techfinance.pessoal.desktop.navigation.Navigator;
import com.techfinance.pessoal.desktop.service.AuthService;
import com.techfinance.pessoal.desktop.util.FxTasks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private final AuthService authService;
    private final Navigator navigator;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @Inject
    public LoginController(AuthService authService, Navigator navigator) {
        this.authService = authService;
        this.navigator = navigator;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isBlank(username)) {
            showError("Informe seu usuário.");
            return;
        }

        if (isBlank(password)) {
            showError("Informe sua senha.");
            return;
        }

        FxTasks.run(
            () -> authService.login(new LoginRequest(username, password)),
            navigator::toHome,
            error -> showError(resolveMessage(error))
        );
    }

    @FXML
    private void goToRegister() {
        navigator.toRegister();
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
            : "Erro ao realizar login.";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
