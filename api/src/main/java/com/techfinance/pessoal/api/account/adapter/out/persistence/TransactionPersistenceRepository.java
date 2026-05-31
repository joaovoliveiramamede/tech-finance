package com.techfinance.pessoal.api.account.adapter.out.persistence;

import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.account.domain.model.Transaction;
import com.techfinance.pessoal.api.account.domain.port.out.TransactionRepository;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;
import com.techfinance.pessoal.api.infra.spring.persistence.TransactionPersistenceSpring;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransactionPersistenceRepository implements TransactionRepository {
    
    private final TransactionPersistenceSpring repository;
    
    @Override
    public Transaction save(Transaction entity) throws UnexpectedErrorException {
        try {

            log.info("salvando transação | accountId={}", entity.getAccount().getId());
            return repository.save(entity);

        } catch (Exception exception) {
            log.error("erro ao salvar transação | accountId={}", entity.getAccount().getId());
            throw new UnexpectedErrorException("erro ao salvar transação", exception);
        }
    }
}