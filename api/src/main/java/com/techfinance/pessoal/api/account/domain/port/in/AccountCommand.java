package com.techfinance.pessoal.api.account.domain.port.in;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.AccountRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.AccountResponse;
import com.techfinance.pessoal.api.infra.exception.BussinessErrorException;

public interface AccountCommand {
    AccountResponse create(AccountRequest request) throws BussinessErrorException;
}