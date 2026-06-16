package com.techfinance.pessoal.desktop.controller;

import java.math.BigDecimal;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.navigation.AppNavigator;
import com.techfinance.pessoal.desktop.service.AccountApiService;
import com.techfinance.pessoal.desktop.util.FxTasks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateAccountController {

    private final AccountApiService accountApiService;

    @FXML
    private TextField nameField;

    @FXML
    private TextField balanceField;

    @FXML
    private Label errorLabel;

    @Inject
    public CreateAccountController(AccountApiService accountApiService) {
        this.accountApiService = accountApiService;
    }

    @FXML
    private void handleCreate() {
        try {
            String name = nameField.getText().trim();

            if (name.isBlank()) {
                showError("Informe o nome da conta.");
                return;
            }

            BigDecimal balance = new BigDecimal(
                    balanceField.getText().trim().replace(",", ".")
            );

            FxTasks.run(
                    () -> accountApiService.createAccount(name, balance),
                    created -> AppNavigator.navigateToHome(),
                    error -> showError(resolveMessage(error))
            );

        } catch (NumberFormatException exception) {
            showError("Saldo inválido.");
        }
    }

    @FXML
    private void goToHome() {
        AppNavigator.navigateToHome();
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private String resolveMessage(Throwable error) {
        Throwable cause = error.getCause() != null ? error.getCause() : error;
        return cause.getMessage() != null
                ? cause.getMessage()
                : "Erro ao criar conta.";
    }
}