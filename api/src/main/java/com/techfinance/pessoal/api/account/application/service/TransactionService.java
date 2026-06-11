package com.techfinance.pessoal.api.account.application.service;

import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.TransactionRequest;
import com.techfinance.pessoal.api.account.application.mapper.TransactionMapper;
import com.techfinance.pessoal.api.account.domain.model.Transaction;
import com.techfinance.pessoal.api.account.domain.port.in.TransactionCommand;
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

    @Override
    public TransactionResult create(TransactionRequest request) throws BussinessErrorException {
        try {
            log.debug(LogMessages.START, "criação", "transacao");
            log.info("criando transacao | transactionAccountId={}", request.accountId());
            Transaction entity = mapper.toEntity(request);



        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
