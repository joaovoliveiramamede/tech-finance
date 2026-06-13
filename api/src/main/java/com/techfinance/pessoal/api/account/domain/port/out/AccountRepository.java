package com.techfinance.pessoal.api.account.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.model.Account;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;

public interface AccountRepository {
    Account save(Account entity) throws UnexpectedErrorException;
    Optional<Account> findByIdAndUserId(UUID id, UUID userId) throws UnexpectedErrorException;
    List<Account> findAllByUserId(UUID userId) throws UnexpectedErrorException;
}
