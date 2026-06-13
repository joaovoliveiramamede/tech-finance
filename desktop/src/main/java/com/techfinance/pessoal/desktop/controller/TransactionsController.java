package com.techfinance.pessoal.desktop.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.dto.enums.TransactionType;
import com.techfinance.pessoal.desktop.dto.request.TransactionRequest;
import com.techfinance.pessoal.desktop.dto.response.AccountResponse;
import com.techfinance.pessoal.desktop.dto.response.CategoryResponse;
import com.techfinance.pessoal.desktop.dto.response.TransactionResponse;
import com.techfinance.pessoal.desktop.service.AccountApiService;
import com.techfinance.pessoal.desktop.service.CategoryApiService;
import com.techfinance.pessoal.desktop.service.TransactionApiService;
import com.techfinance.pessoal.desktop.util.Formatters;
import com.techfinance.pessoal.desktop.util.FxTasks;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

public class TransactionsController {

    private final TransactionApiService transactionApiService;
    private final AccountApiService accountApiService;
    private final CategoryApiService categoryApiService;

    private Map<UUID, String> accountNames = Map.of();
    private Map<UUID, String> categoryNames = Map.of();

    @FXML
    private TableView<TransactionResponse> transactionsTable;

    @FXML
    private TableColumn<TransactionResponse, String> dateColumn;

    @FXML
    private TableColumn<TransactionResponse, String> descriptionColumn;

    @FXML
    private TableColumn<TransactionResponse, String> accountColumn;

    @FXML
    private TableColumn<TransactionResponse, String> categoryColumn;

    @FXML
    private TableColumn<TransactionResponse, String> typeColumn;

    @FXML
    private TableColumn<TransactionResponse, String> amountColumn;

    @FXML
    private Button createButton;

    @FXML
    private Label errorLabel;

    @Inject
    public TransactionsController(
            TransactionApiService transactionApiService,
            AccountApiService accountApiService,
            CategoryApiService categoryApiService) {

        this.transactionApiService = transactionApiService;
        this.accountApiService = accountApiService;
        this.categoryApiService = categoryApiService;
    }

    @FXML
    private void initialize() {
        dateColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                Formatters.dateTime(data.getValue().occurredAt())
            )
        );

        descriptionColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().description())
        );

        accountColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty("-")
        );

        categoryColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty("-")
        );

        typeColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().type() != null ? data.getValue().type().name() : "-"
            )
        );

        amountColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                Formatters.currency(data.getValue().amount())
            )
        );

        loadData();
    }

    @FXML
    private void handleCreate() {
        FxTasks.run(
            () -> new FormData(accountApiService.findAll(), categoryApiService.findAll()),
            this::openCreateDialog,
            error -> showError(resolveMessage(error))
        );
    }

    private void openCreateDialog(FormData formData) {
        if (formData.accounts().isEmpty()) {
            showError("Cadastre uma conta antes de lançar transações.");
            return;
        }

        if (formData.categories().isEmpty()) {
            showError("Cadastre uma categoria antes de lançar transações.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nova transação");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        ComboBox<AccountResponse> accountCombo = new ComboBox<>(
            FXCollections.observableArrayList(formData.accounts())
        );
        accountCombo.setConverter(accountLabelConverter());
        accountCombo.getSelectionModel().selectFirst();

        ComboBox<CategoryResponse> categoryCombo = new ComboBox<>(
            FXCollections.observableArrayList(formData.categories())
        );
        categoryCombo.setConverter(categoryLabelConverter());
        categoryCombo.getSelectionModel().selectFirst();

        ComboBox<TransactionType> typeCombo = new ComboBox<>(
            FXCollections.observableArrayList(TransactionType.values())
        );
        typeCombo.getSelectionModel().selectFirst();

        TextField amountField = new TextField();
        amountField.setPromptText("Valor");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Descrição");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Conta"), accountCombo);
        grid.addRow(1, new Label("Categoria"), categoryCombo);
        grid.addRow(2, new Label("Tipo"), typeCombo);
        grid.addRow(3, new Label("Valor"), amountField);
        grid.addRow(4, new Label("Descrição"), descriptionField);
        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(result -> {
            if (result != ButtonType.OK) {
                return;
            }

            try {
                BigDecimal amount = new BigDecimal(amountField.getText().replace(",", "."));
                var request = new TransactionRequest(
                    accountCombo.getValue().id(),
                    categoryCombo.getValue().id(),
                    amount,
                    typeCombo.getValue(),
                    descriptionField.getText().trim(),
                    Formatters.transactionApiDate(Instant.now())
                );

                FxTasks.run(
                    () -> transactionApiService.create(request),
                    created -> loadData(),
                    error -> showError(resolveMessage(error))
                );
            } catch (NumberFormatException exception) {
                showError("Valor inválido.");
            }
        });
    }

    private void loadData() {
        FxTasks.run(
            () -> new TransactionsPageData(
                accountApiService.findAll(),
                categoryApiService.findAll(),
                transactionApiService.findAll()
            ),
            data -> {
                accountNames = data.accounts().stream()
                    .collect(Collectors.toMap(AccountResponse::id, AccountResponse::name));

                categoryNames = data.categories().stream()
                    .collect(Collectors.toMap(CategoryResponse::id, CategoryResponse::name));

                renderTransactions(data.transactions());
            },
            error -> showError(resolveMessage(error))
        );
    }

    private void renderTransactions(List<TransactionResponse> transactions) {
        transactionsTable.setItems(FXCollections.observableArrayList(transactions));
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
        return cause.getMessage() != null ? cause.getMessage() : "Erro ao processar transação.";
    }

    private record FormData(
        List<AccountResponse> accounts,
        List<CategoryResponse> categories
    ) {}

    private record TransactionsPageData(
        List<AccountResponse> accounts,
        List<CategoryResponse> categories,
        List<TransactionResponse> transactions
    ) {}

    private StringConverter<AccountResponse> accountLabelConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(AccountResponse account) {
                return account != null ? account.name() : "";
            }

            @Override
            public AccountResponse fromString(String string) {
                return null;
            }
        };
    }

    private StringConverter<CategoryResponse> categoryLabelConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(CategoryResponse category) {
                return category != null ? category.name() : "";
            }

            @Override
            public CategoryResponse fromString(String string) {
                return null;
            }
        };
    }
}
