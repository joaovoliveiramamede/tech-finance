package com.techfinance.pessoal.api.account.application.mapper;

import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.AccountRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.AccountResponse;
import com.techfinance.pessoal.api.account.domain.model.Account;
import com.techfinance.pessoal.api.infra.shared.converter.MapperConverter;

@Component
public class AccountMapper 
    extends MapperConverter<AccountRequest, AccountResponse, Account> {
    
    @Override
    protected Account doToEntity(AccountRequest request) {
        return Account.builder()
            .name(request.name())
            .balance(request.balance())
            .type(request.type())
            .build();
    }

    @Override
    protected AccountResponse doToResponse(Account entity) {
        return new AccountResponse(
            entity.getId(),
            entity.getName(),
            entity.getBalance(),
            entity.getType(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

}