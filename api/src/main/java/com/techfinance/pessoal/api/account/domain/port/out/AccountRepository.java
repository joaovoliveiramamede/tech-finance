package com.techfinance.pessoal.api.account.domain.port.out;

import java.util.Optional;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.model.Account;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;

public interface AccountRepository {
    Account save(Account entity) throws UnexpectedErrorException;
    Optional<Account> findById(UUID id) throws UnexpectedErrorException;
}
