package com.techfinance.pessoal.api.user.domain.port.in;

import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;
import com.techfinance.pessoal.api.infra.exception.NotFoundErrorException;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.domain.port.out.result.UserResult;

public interface UserUseCase {
    UserResult create(UserRequest request) throws BusinessErrorException;
    UserResult byUsername(String username) throws NotFoundErrorException, BusinessErrorException;
}
