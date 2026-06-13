package com.techfinance.pessoal.api.account.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.AccountRequest;
import com.techfinance.pessoal.api.account.application.mapper.AccountMapper;
import com.techfinance.pessoal.api.account.domain.model.Account;
import com.techfinance.pessoal.api.account.domain.port.in.AccountCommand;
import com.techfinance.pessoal.api.account.domain.port.in.AccountUseCase;
import com.techfinance.pessoal.api.account.domain.port.out.AccountRepository;
import com.techfinance.pessoal.api.account.domain.port.out.result.AccountResult;
import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;
import com.techfinance.pessoal.api.infra.exception.NotFoundErrorException;
import com.techfinance.pessoal.api.infra.shared.log.LogMessages;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountService implements AccountCommand, AccountUseCase {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public AccountResult create(AccountRequest request) throws BusinessErrorException {
        try {
            log.debug(LogMessages.START, "criação", "conta");
            log.info("criando conta | accountName={}", request.name());

            Account entity = mapper.toEntity(request);
            Account saved = repository.save(entity);

            log.info("conta criada com sucesso | accountId={}", saved.getId());
            log.debug(LogMessages.FINISH, "criação", "conta");

            return mapper.toResult(saved);
        } catch (Exception exception) {
            log.error(LogMessages.BUSINESS_ERROR, "criação", Account.class.getSimpleName());
            throw new BusinessErrorException(LogMessages.BUSINESS_ERROR_EXCEPTION_ACCOUNT, exception);
        }
    }

    @Override
    public AccountResult byId(UUID id) throws NotFoundErrorException, BusinessErrorException {
        try {
            log.debug(LogMessages.START, "busca", "conta");
            log.info("buscando conta | accountId={}", id);

            AccountResult result = repository.findById(id)
                .map(mapper::toResult)
                .orElseThrow(() -> new NotFoundErrorException("conta não encontrada | accountId=" + id));

            log.debug(LogMessages.FINISH, "busca", "conta");
            return result;
        } catch (NotFoundErrorException exception) {
            throw exception;
        } catch (Exception exception) {
            log.error(LogMessages.BUSINESS_ERROR, "busca", Account.class.getSimpleName());
            throw new BusinessErrorException(LogMessages.BUSINESS_ERROR_EXCEPTION_ACCOUNT, exception);
        }
    }
}
