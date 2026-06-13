package com.techfinance.pessoal.api.account.domain.port.in;

import java.util.List;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.TransactionRequest;
import com.techfinance.pessoal.api.account.domain.port.out.result.TransactionResult;
import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;

public interface TransactionUseCase {
    TransactionResult create(TransactionRequest request) throws BusinessErrorException;
    List<TransactionResult> findAll() throws BusinessErrorException;
}
