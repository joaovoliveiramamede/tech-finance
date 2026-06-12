package com.techfinance.pessoal.api.account.application.mapper;

import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.TransactionRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.TransactionResponse;
import com.techfinance.pessoal.api.account.domain.model.Transaction;
import com.techfinance.pessoal.api.account.domain.port.out.result.TransactionResult;
import com.techfinance.pessoal.api.infra.shared.converter.MapperConverter;

@Component
public class TransactionMapper 
    extends MapperConverter<TransactionRequest, TransactionResponse, TransactionResult, Transaction> {
    
    @Override
    protected Transaction doToEntity(TransactionRequest request) {
        return Transaction.builder()
            .amount(request.amount())
            .description(request.description())
            .build();
    }

    @Override
    protected TransactionResult doToResult(Transaction entity) {
        return TransactionResult.builder()
            .amount(entity.getAmount())
            .description(entity.getDescription())
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

}
