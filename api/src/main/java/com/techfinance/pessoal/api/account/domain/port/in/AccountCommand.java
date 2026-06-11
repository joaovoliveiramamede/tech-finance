package com.techfinance.pessoal.api.account.domain.port.in;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.AccountRequest;
import com.techfinance.pessoal.api.account.domain.port.out.result.AccountResult;
import com.techfinance.pessoal.api.infra.exception.BussinessErrorException;

public interface AccountCommand {
    AccountResult create(AccountRequest request) throws BussinessErrorException;
}
