package com.techfinance.pessoal.api.account.domain.port.out;

import java.rmi.UnexpectedException;

import com.techfinance.pessoal.api.account.domain.model.Account;

public interface AccountRepository {
    Account save(Account entity) throws UnexpectedException;
}