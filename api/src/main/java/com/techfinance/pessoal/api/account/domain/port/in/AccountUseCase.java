package com.techfinance.pessoal.api.account.domain.port.in;

import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.port.out.result.AccountResult;
import com.techfinance.pessoal.api.infra.exception.BussinessErrorException;

public interface AccountUseCase {
    AccountResult byId(UUID id) throws BussinessErrorException;
}
