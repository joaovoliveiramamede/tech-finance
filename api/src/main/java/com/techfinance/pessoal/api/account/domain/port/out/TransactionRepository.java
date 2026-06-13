package com.techfinance.pessoal.api.account.domain.port.out;

import java.util.List;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.model.Transaction;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;

public interface TransactionRepository {
    Transaction save(Transaction entity) throws UnexpectedErrorException;
    List<Transaction> findAllByUserId(UUID userId) throws UnexpectedErrorException;
}
