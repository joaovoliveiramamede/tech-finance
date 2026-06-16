package com.techfinance.pessoal.api.account.application.service;



import java.math.BigDecimal;

import java.util.List;

import java.util.UUID;



import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;



import com.techfinance.pessoal.api.account.adapter.in.dto.request.TransactionRequest;

import com.techfinance.pessoal.api.account.application.mapper.TransactionMapper;

import com.techfinance.pessoal.api.account.domain.model.Account;

import com.techfinance.pessoal.api.account.domain.model.Category;

import com.techfinance.pessoal.api.account.domain.model.Transaction;

import com.techfinance.pessoal.api.account.domain.port.in.TransactionUseCase;

import com.techfinance.pessoal.api.account.domain.port.out.AccountRepository;

import com.techfinance.pessoal.api.account.domain.port.out.CategoryRepository;

import com.techfinance.pessoal.api.account.domain.port.out.TransactionRepository;

import com.techfinance.pessoal.api.account.domain.port.out.result.TransactionResult;

import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;

import com.techfinance.pessoal.api.infra.exception.NotFoundErrorException;

import com.techfinance.pessoal.api.infra.security.context.AuthenticatedUserContext;

import com.techfinance.pessoal.api.infra.shared.log.LogMessages;



import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;



@Service

@Log4j2

@RequiredArgsConstructor

public class TransactionService implements TransactionUseCase {



    private final TransactionMapper mapper;

    private final TransactionRepository repository;

    private final AccountRepository accountRepository;

    private final CategoryRepository categoryRepository;

    private final AuthenticatedUserContext authenticatedUserContext;



    @Override
    @Transactional
    public TransactionResult create(TransactionRequest request) throws BusinessErrorException {
        try {

            UUID userId = authenticatedUserContext.getCurrentUserId();

            log.debug(LogMessages.START, "criação", "transação");

            log.info("criando transação | accountId={} | userId={}", request.accountId(), userId);

            validate(request);



            Account account = accountRepository.findByIdAndUserId(request.accountId(), userId)

                .orElseThrow(() -> new NotFoundErrorException(

                    "conta não encontrada | accountId=" + request.accountId()));



            Category category = categoryRepository.findByIdAndUserId(request.categoryId(), userId)

                .orElseThrow(() -> new NotFoundErrorException(

                    "categoria não encontrada | categoryId=" + request.categoryId()));



            Transaction entity = mapper.toEntityWithAccountAndCategory(request, account, category);



            account.applyTransaction(entity);

            accountRepository.save(account);



            Transaction saved = repository.save(entity);

            log.info("transação criada com sucesso | transactionId={}", saved.getId());

            log.debug(LogMessages.FINISH, "criação", "transação");



            return mapper.toResult(saved);

        } catch (NotFoundErrorException exception) {

            throw exception;

        } catch (IllegalArgumentException | IllegalStateException exception) {

            throw new BusinessErrorException(exception.getMessage(), exception);

        } catch (BusinessErrorException exception) {

            throw exception;

        } catch (Exception exception) {

            log.error(LogMessages.BUSINESS_ERROR, "criação", Transaction.class.getSimpleName());

            throw new BusinessErrorException(LogMessages.BUSINESS_ERROR_EXCEPTION_TRANSACTION, exception);

        }

    }



    @Override

    public List<TransactionResult> findAll() throws BusinessErrorException {

        try {

            UUID userId = authenticatedUserContext.getCurrentUserId();



            log.debug(LogMessages.START, "listagem", "transação");

            log.info("listando transações | userId={}", userId);



            List<TransactionResult> results = repository.findAllByUserId(userId).stream()

                .map(mapper::toResult)

                .toList();



            log.debug(LogMessages.FINISH, "listagem", "transação");

            return results;

        } catch (Exception exception) {

            log.error(LogMessages.BUSINESS_ERROR, "listagem", Transaction.class.getSimpleName());

            throw new BusinessErrorException(LogMessages.BUSINESS_ERROR_EXCEPTION_TRANSACTION, exception);

        }

    }



    private void validate(TransactionRequest request) {

        if (request.amount() == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException("valor da transação deve ser maior que zero");

        }



        if (request.type() == null) {

            throw new IllegalArgumentException("tipo da transação é obrigatório");

        }



        if (request.occurredAt() == null) {

            throw new IllegalArgumentException("data da transação é obrigatória");

        }

    }

}

