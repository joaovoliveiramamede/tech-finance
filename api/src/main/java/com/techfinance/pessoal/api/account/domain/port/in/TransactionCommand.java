package com.techfinance.pessoal.api.account.domain.port.in;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.TransactionRequest;
import com.techfinance.pessoal.api.account.domain.port.out.result.TransactionResult;
import com.techfinance.pessoal.api.infra.exception.BussinessErrorException;

public interface TransactionCommand {
    TransactionResult create(TransactionRequest request) throws BussinessErrorException;
}
