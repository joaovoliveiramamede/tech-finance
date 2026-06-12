package com.techfinance.pessoal.api.account.application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.TransactionRequest;
import com.techfinance.pessoal.api.account.application.mapper.TransactionMapper;
import com.techfinance.pessoal.api.account.domain.model.Account;
import com.techfinance.pessoal.api.account.domain.model.Category;
import com.techfinance.pessoal.api.account.domain.model.Transaction;
import com.techfinance.pessoal.api.account.domain.port.in.TransactionCommand;
import com.techfinance.pessoal.api.account.domain.port.out.AccountRepository;
import com.techfinance.pessoal.api.account.domain.port.out.CategoryRepository;
import com.techfinance.pessoal.api.account.domain.port.out.TransactionRepository;
import com.techfinance.pessoal.api.account.domain.port.out.result.TransactionResult;
import com.techfinance.pessoal.api.infra.exception.BussinessErrorException;
import com.techfinance.pessoal.api.infra.shared.log.LogMessages;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransactionService 
    implements TransactionCommand {
    
    private final TransactionMapper mapper;
    private final TransactionRepository repository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public TransactionResult create(TransactionRequest request) throws BussinessErrorException {
        try {
            log.debug(LogMessages.START, "criação", "transacao");
            log.info("criando transacao | transactionAccountId={}", request.accountId());
            validate(request);
            
            Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new BussinessErrorException("conta nao encontrada"));

            Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new BussinessErrorException("categoria nao encontrada"));

            Transaction entity = mapper.toEntityWithAccountAndCategory(request, account, category);

            account.applyTransaction(entity);
            accountRepository.save(account);

            Transaction saved = repository.save(entity);
            log.info("transacao criada com sucesso | transactionId={}", saved.getId());

            TransactionResult result = mapper.toResult(saved);

            log.debug(LogMessages.FINISH, "criação", "transacao");
            return result;
        } catch (IllegalArgumentException | IllegalStateException exception) {
            throw new BussinessErrorException(exception.getMessage(), exception);
        } catch (BussinessErrorException exception) {
            throw exception;
        } catch (Exception exception) {
            log.error("erro ao criar transacao | message={}", exception.getMessage());
            throw new BussinessErrorException("erro ao criar transacao", exception);
        }
    }

    private void validate(TransactionRequest request) {
        if (request.amount() == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("valor da transacao deve ser maior que zero");
        }

        if (request.type() == null) {
            throw new IllegalArgumentException("tipo da transacao e obrigatorio");
        }

        if (request.occurredAt() == null) {
            throw new IllegalArgumentException("data da transacao e obrigatoria");
        }
    }

}
