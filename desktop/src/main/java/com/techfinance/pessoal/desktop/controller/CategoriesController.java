package com.techfinance.pessoal.desktop.controller;

import java.util.List;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.dto.request.CategoryRequest;
import com.techfinance.pessoal.desktop.dto.response.CategoryResponse;
import com.techfinance.pessoal.desktop.service.CategoryApiService;
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

public class CategoriesController {

    private final CategoryApiService categoryApiService;

    @FXML
    private TableView<CategoryResponse> categoriesTable;

    @FXML
    private TableColumn<CategoryResponse, String> nameColumn;

    @FXML
    private TableColumn<CategoryResponse, String> descriptionColumn;

    @FXML
    private TableColumn<CategoryResponse, String> createdAtColumn;

    @FXML
    private Button createButton;

    @FXML
    private Label errorLabel;

    @Inject
    public CategoriesController(CategoryApiService categoryApiService) {
        this.categoryApiService = categoryApiService;
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().name())
        );

        descriptionColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().description())
        );

        createdAtColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                Formatters.dateTime(data.getValue().createdAt())
            )
        );

        loadCategories();
    }

    @FXML
    private void handleCreate() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nova categoria");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField nameField = new TextField();
        nameField.setPromptText("Nome");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Descrição");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Nome"), nameField);
        grid.addRow(1, new Label("Descrição"), descriptionField);
        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(result -> {
            if (result != ButtonType.OK) {
                return;
            }

            var request = new CategoryRequest(
                nameField.getText().trim(),
                descriptionField.getText().trim()
            );

            FxTasks.run(
                () -> categoryApiService.create(request),
                created -> loadCategories(),
                error -> showError(resolveMessage(error))
            );
        });
    }

    private void loadCategories() {
        FxTasks.run(
            categoryApiService::findAll,
            this::renderCategories,
            error -> showError(resolveMessage(error))
        );
    }

    private void renderCategories(List<CategoryResponse> categories) {
        categoriesTable.setItems(FXCollections.observableArrayList(categories));
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
        return cause.getMessage() != null ? cause.getMessage() : "Erro ao processar categoria.";
    }
}
