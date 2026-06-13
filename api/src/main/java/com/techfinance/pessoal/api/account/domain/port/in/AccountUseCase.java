package com.techfinance.pessoal.api.account.domain.port.in;

import java.util.List;
import java.util.UUID;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.AccountRequest;
import com.techfinance.pessoal.api.account.domain.port.out.result.AccountResult;
import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;
import com.techfinance.pessoal.api.infra.exception.NotFoundErrorException;

public interface AccountUseCase {
    AccountResult create(AccountRequest request) throws BusinessErrorException;
    AccountResult byId(UUID id) throws NotFoundErrorException, BusinessErrorException;
    List<AccountResult> findAll() throws BusinessErrorException;
}
