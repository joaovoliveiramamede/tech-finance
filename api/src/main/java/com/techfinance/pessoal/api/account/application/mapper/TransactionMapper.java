package com.techfinance.pessoal.api.account.application.mapper;

import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.TransactionRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.TransactionResponse;
import com.techfinance.pessoal.api.account.domain.model.Account;
import com.techfinance.pessoal.api.account.domain.model.Category;
import com.techfinance.pessoal.api.account.domain.model.Transaction;
import com.techfinance.pessoal.api.account.domain.port.out.result.AccountResult;
import com.techfinance.pessoal.api.account.domain.port.out.result.CategoryResult;
import com.techfinance.pessoal.api.account.domain.port.out.result.TransactionResult;
import com.techfinance.pessoal.api.infra.shared.converter.MapperConverter;

@Component
public class TransactionMapper 
    extends MapperConverter<TransactionRequest, TransactionResponse, TransactionResult, Transaction> {
    
    @Override
    protected Transaction doToEntity(TransactionRequest request) {
        return Transaction.builder()
            .amount(request.amount())
            .type(request.type())
            .description(request.description())
            .occurredAt(request.occurredAt())
            .build();
    }

    @Override
    protected TransactionResult doToResult(Transaction entity) {
        return TransactionResult.builder()
            .id(entity.getId())
            .amount(entity.getAmount())
            .type(entity.getType())
            .description(entity.getDescription())
            .occurredAt(entity.getOccurredAt())
            .account(toAccountResult(entity.getAccount()))
            .category(toCategoryResult(entity.getCategory()))
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    @Override
    protected TransactionResponse doToResponse(TransactionResult result) {
        return new TransactionResponse(
            result.getId(),
            result.getAmount(),
            result.getType(),
            result.getDescription(),
            result.getOccurredAt(),
            result.getCreatedAt(),
            result.getUpdatedAt()
        );
    }

    public Transaction toEntityWithAccountAndCategory(TransactionRequest request, Account account, Category category) {
        return Transaction.builder()
            .amount(request.amount())
            .type(request.type())
            .description(request.description())
            .occurredAt(request.occurredAt())
            .account(account)
            .category(category)
            .build();
    }

    private AccountResult toAccountResult(Account account) {
        if (account == null) {
            return null;
        }

        return AccountResult.builder()
            .id(account.getId())
            .name(account.getName())
            .balance(account.getBalance())
            .type(account.getType())
            .createdAt(account.getCreatedAt())
            .updatedAt(account.getUpdatedAt())
            .build();
    }

    private CategoryResult toCategoryResult(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResult.builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .createdAt(category.getCreatedAt())
            .updatedAt(category.getUpdatedAt())
            .build();
    }

}
