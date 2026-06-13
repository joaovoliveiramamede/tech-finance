package com.techfinance.pessoal.api.account.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Account> findByIdAndUserId(UUID id, UUID userId) throws UnexpectedErrorException {
        try {
            log.info("buscando conta no banco de dados | accountId={} | userId={}", id, userId);
            return repository.findByIdAndUser_Id(id, userId);
        } catch (Exception exception) {
            log.error("erro ao buscar conta no banco de dados | accountId={}", id);
            throw new UnexpectedErrorException("erro ao buscar conta no banco de dados", exception);
        }
    }

    @Override
    public List<Account> findAllByUserId(UUID userId) throws UnexpectedErrorException {
        try {
            log.info("listando contas do usuário | userId={}", userId);
            return repository.findByUser_IdOrderByCreatedAtDesc(userId);
        } catch (Exception exception) {
            log.error("erro ao listar contas do usuário | userId={}", userId);
            throw new UnexpectedErrorException("erro ao listar contas do usuário", exception);
        }
    }
}
