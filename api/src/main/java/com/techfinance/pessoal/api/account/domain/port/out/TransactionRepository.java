package com.techfinance.pessoal.api.account.domain.port.out;

import com.techfinance.pessoal.api.account.domain.model.Transaction;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;

public interface TransactionRepository {
    Transaction save(Transaction entity) throws UnexpectedErrorException;
}