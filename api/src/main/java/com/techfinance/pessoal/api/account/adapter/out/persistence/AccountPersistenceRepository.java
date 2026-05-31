package com.techfinance.pessoal.api.account.adapter.out.persistence;

import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.account.domain.model.Account;
import com.techfinance.pessoal.api.account.domain.port.out.AccountRepository;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;
import com.techfinance.pessoal.api.infra.spring.persistence.AccountPersistenceSpring;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountPersistenceRepository implements AccountRepository {

    private final AccountPersistenceSpring repository;

    @Override
    public Account save(Account entity) throws UnexpectedErrorException {
        try {

            log.info("salvando conta no banco de dados");
            return repository.save(entity);
        
        } catch (Exception exception) {

            log.error("erro ao salvar conta no banco dados");
            throw new UnexpectedErrorException("erro ao salvar conta no banco de dados", exception);
        
        }
    }

}