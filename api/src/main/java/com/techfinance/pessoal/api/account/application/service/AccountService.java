package com.techfinance.pessoal.api.account.application.service;

import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.AccountRequest;
import com.techfinance.pessoal.api.account.application.mapper.AccountMapper;
import com.techfinance.pessoal.api.account.domain.model.Account;
import com.techfinance.pessoal.api.account.domain.port.in.AccountCommand;
import com.techfinance.pessoal.api.account.domain.port.out.AccountRepository;
import com.techfinance.pessoal.api.account.domain.port.out.result.AccountResult;
import com.techfinance.pessoal.api.infra.exception.BussinessErrorException;
import com.techfinance.pessoal.api.infra.shared.log.LogMessages;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
@RequiredArgsConstructor
public class AccountService 
    implements AccountCommand {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public AccountResult create(AccountRequest request) throws BussinessErrorException {
        try {
            log.debug(LogMessages.START, "criação", "conta");
            log.info("criando conta | accountName={}", request.name());
            Account entity = mapper.toEntity(request);

            log.info(LogMessages.START_SAVED_DATABASE, "criação", Account.class.getName().toString());
            
            Account saved = repository.save(entity);
            log.info("saved entity: {}", saved);
            log.info(LogMessages.FINISH_SAVED_DATABASE, "criação");

            return mapper.toResult(saved);
        } catch (Exception exception) {
            log.error(LogMessages.BUSSINESS_ERROR, "criação", Account.class.getName().toString());
            throw new BussinessErrorException(LogMessages.BUSSINESS_ERROR_EXCEPTION_CATEGORY, exception);
        }


    }

}
