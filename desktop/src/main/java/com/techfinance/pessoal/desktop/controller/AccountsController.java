package com.techfinance.pessoal.desktop.controller;

import java.math.BigDecimal;
import java.util.List;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.dto.response.AccountResponse;
import com.techfinance.pessoal.desktop.service.AccountApiService;
import com.techfinance.pessoal.desktop.util.Formatters;
import com.techfinance.pessoal.desktop.util.FxTasks;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AccountsController {

    private final AccountApiService accountApiService;

    @FXML
    private TableView<AccountResponse> accountsTable;

    @FXML
    private TableColumn<AccountResponse, String> nameColumn;

    @FXML
    private TableColumn<AccountResponse, String> typeColumn;

    @FXML
    private TableColumn<AccountResponse, String> balanceColumn;

    @FXML
    private TableColumn<AccountResponse, String> createdAtColumn;

    @FXML
    private TableColumn<AccountResponse, String> updatedAtColumn;

    @FXML
    private Button createButton;

    @FXML
    private Label errorLabel;

    @Inject
    public AccountsController(AccountApiService accountApiService) {
        this.accountApiService = accountApiService;
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().name())
        );

        typeColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().type() != null ? data.getValue().type().name() : "-"
            )
        );

        balanceColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                Formatters.currency(data.getValue().balance())
            )
        );

        createdAtColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                Formatters.dateTime(data.getValue().createdAt())
            )
        );

        updatedAtColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                Formatters.dateTime(data.getValue().updatedAt())
            )
        );

        loadAccounts();
    }

    @FXML
    private void handleCreate() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nova conta");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField nameField = new TextField();
        nameField.setPromptText("Nome da conta");

        TextField balanceField = new TextField();
        balanceField.setPromptText("Saldo inicial");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Nome"), nameField);
        grid.addRow(1, new Label("Saldo"), balanceField);
        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(result -> {
            if (result != ButtonType.OK) {
                return;
            }

            try {
                BigDecimal balance = new BigDecimal(balanceField.getText().replace(",", "."));

                FxTasks.run(
                    () -> accountApiService.createAccount(nameField.getText().trim(), balance),
                    created -> loadAccounts(),
                    error -> showError(resolveMessage(error))
                );
            } catch (NumberFormatException exception) {
                showError("Saldo inválido.");
            }
        });
    }

    private void loadAccounts() {
        FxTasks.run(
            accountApiService::findAll,
            this::renderAccounts,
            error -> showError(resolveMessage(error))
        );
    }

    private void renderAccounts(List<AccountResponse> accounts) {
        accountsTable.setItems(FXCollections.observableArrayList(accounts));
        hideError();
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void hideError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    private String resolveMessage(Throwable error) {
        Throwable cause = error.getCause() != null ? error.getCause() : error;
        return cause.getMessage() != null ? cause.getMessage() : "Erro ao processar conta.";
    }
}
