package com.techfinance.pessoal.desktop.controller;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.dto.request.RegisterRequest;
import com.techfinance.pessoal.desktop.navigation.Navigator;
import com.techfinance.pessoal.desktop.service.AuthService;
import com.techfinance.pessoal.desktop.util.FxTasks;
import com.techfinance.pessoal.desktop.util.ValidationField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    private final AuthService authService;
    private final Navigator navigator;

    @Inject
    public RegisterController(AuthService authService, Navigator navigator) {
        this.authService = authService;
        this.navigator = navigator;
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
        String name = ValidationField.normalize(nameField.getText());
        String username = ValidationField.normalize(usernameField.getText());
        String password = passwordField.getText();

        var validationError = ValidationField.validateRegister(name, username, password);
        if (validationError.isPresent()) {
            showError(validationError.get());
            return;
        }

        FxTasks.run(
            () -> authService.register(
                new RegisterRequest(name, username, password, "USER")
            ),
            navigator::toCreateAccount,
            error -> showError(resolveMessage(error))
        );
    }

    @FXML
    private void goToLogin() {
        navigator.toLogin();
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
}
