package com.techfinance.pessoal.api.account.application.mapper;

import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.TransactionRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.TransactionResponse;
import com.techfinance.pessoal.api.account.domain.model.Transaction;
import com.techfinance.pessoal.api.account.domain.port.out.result.TransactionResult;
import com.techfinance.pessoal.api.infra.shared.converter.MapperConverter;

@Component
public class TransactionMapper 
    extends MapperConverter<TransactionRequest, TransactionResponse, TransactionResult, Transaction> {
    
    @Override
    protected Transaction doToEntity(TransactionRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected TransactionResult doToResult(Transaction entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected TransactionResponse doToResponse(TransactionResult reult) {
        // TODO Auto-generated method stub
        return null;
    }

}
