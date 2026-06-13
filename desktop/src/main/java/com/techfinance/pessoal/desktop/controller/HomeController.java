package com.techfinance.pessoal.desktop.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;

import com.google.inject.Inject;
import com.techfinance.pessoal.desktop.dto.enums.TransactionType;
import com.techfinance.pessoal.desktop.dto.response.AccountResponse;
import com.techfinance.pessoal.desktop.dto.response.CategoryResponse;
import com.techfinance.pessoal.desktop.dto.response.TransactionResponse;
import com.techfinance.pessoal.desktop.service.AccountApiService;
import com.techfinance.pessoal.desktop.service.CategoryApiService;
import com.techfinance.pessoal.desktop.service.TransactionApiService;
import com.techfinance.pessoal.desktop.util.Formatters;
import com.techfinance.pessoal.desktop.util.FxTasks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {

    private final AccountApiService accountApiService;
    private final CategoryApiService categoryApiService;
    private final TransactionApiService transactionApiService;

    @FXML
    private Label totalBalanceLabel;

    @FXML
    private Label incomeLabel;

    @FXML
    private Label expenseLabel;

    @FXML
    private Label lastTransactionsLabel;

    @FXML
    private Label topCategoriesLabel;

    @Inject
    public HomeController(
            AccountApiService accountApiService,
            CategoryApiService categoryApiService,
            TransactionApiService transactionApiService) {

        this.accountApiService = accountApiService;
        this.categoryApiService = categoryApiService;
        this.transactionApiService = transactionApiService;
    }

    @FXML
    private void initialize() {
        loadDashboard();
    }

    private void loadDashboard() {
        FxTasks.run(
            () -> {
                List<AccountResponse> accounts = accountApiService.findAll();
                List<TransactionResponse> transactions = transactionApiService.findAll();
                List<CategoryResponse> categories = categoryApiService.findAll();
                return new DashboardData(accounts, transactions, categories);
            },
            this::renderDashboard,
            error -> {
                totalBalanceLabel.setText("R$ 0,00");
                lastTransactionsLabel.setText("Erro ao carregar dados.");
            }
        );
    }

    private void renderDashboard(DashboardData data) {
        BigDecimal totalBalance = data.accounts().stream()
            .map(AccountResponse::balance)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        var currentMonth = java.time.YearMonth.now(ZoneId.of("America/Sao_Paulo"));

        BigDecimal income = data.transactions().stream()
            .filter(transaction -> transaction.type() == TransactionType.INCOME)
            .filter(transaction -> isCurrentMonth(transaction.occurredAt(), currentMonth))
            .map(TransactionResponse::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = data.transactions().stream()
            .filter(transaction -> transaction.type() == TransactionType.EXPENSE)
            .filter(transaction -> isCurrentMonth(transaction.occurredAt(), currentMonth))
            .map(TransactionResponse::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalBalanceLabel.setText(Formatters.currency(totalBalance));
        incomeLabel.setText(Formatters.currency(income));
        expenseLabel.setText(Formatters.currency(expense));

        String lastTransactions = data.transactions().stream()
            .sorted(Comparator.comparing(TransactionResponse::occurredAt).reversed())
            .limit(5)
            .map(transaction -> Formatters.dateTime(transaction.occurredAt())
                + " • "
                + transaction.description()
                + " • "
                + Formatters.currency(transaction.amount()))
            .reduce((left, right) -> left + "\n" + right)
            .orElse("Nenhuma transação registrada");

        lastTransactionsLabel.setText(lastTransactions);

        String categories = data.categories().stream()
            .limit(5)
            .map(CategoryResponse::name)
            .reduce((left, right) -> left + "\n" + right)
            .orElse("Nenhuma categoria registrada");

        topCategoriesLabel.setText(categories);
    }

    private boolean isCurrentMonth(Instant instant, java.time.YearMonth month) {
        if (instant == null) {
            return false;
        }

        var transactionMonth = java.time.YearMonth.from(
            instant.atZone(ZoneId.of("America/Sao_Paulo"))
        );

        return transactionMonth.equals(month);
    }

    private record DashboardData(
        List<AccountResponse> accounts,
        List<TransactionResponse> transactions,
        List<CategoryResponse> categories
    ) {}
}
