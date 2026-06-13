package com.techfinance.pessoal.api.account.application.mapper;

import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.AccountRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.AccountResponse;
import com.techfinance.pessoal.api.account.domain.model.Account;
import com.techfinance.pessoal.api.account.domain.port.out.result.AccountResult;
import com.techfinance.pessoal.api.infra.shared.converter.MapperConverter;

@Component
public class AccountMapper 
    extends MapperConverter<AccountRequest, AccountResponse, AccountResult, Account> {
    
    @Override
    protected Account doToEntity(AccountRequest request) {
        return Account.builder()
            .name(request.name())
            .balance(request.balance())
            .type(request.type())
            .build();
    }

    @Override
    protected AccountResult doToResult(Account entity) {
        return AccountResult.builder()
            .id(entity.getId())
            .userId(entity.getUser() != null ? entity.getUser().getId() : null)
            .name(entity.getName())
            .type(entity.getType())
            .balance(entity.getBalance())
            .updatedAt(entity.getUpdatedAt())
            .createdAt(entity.getCreatedAt())
            .build();
    }

    @Override
    protected AccountResponse doToResponse(AccountResult result) {
        return new AccountResponse(
            result.getId(), 
            result.getName(),
            result.getBalance(),
            result.getType(),
            result.getUserId(),
            result.getCreatedAt(), 
            result.getUpdatedAt()
        );

    }
}