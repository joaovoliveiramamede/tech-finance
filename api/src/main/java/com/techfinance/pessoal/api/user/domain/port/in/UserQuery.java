package com.techfinance.pessoal.api.user.domain.port.in;

import com.techfinance.pessoal.api.infra.security.exception.NotFoundErrorException;
import com.techfinance.pessoal.api.user.adapter.in.dto.response.UserResponse;
import com.techfinance.pessoal.api.user.application.exception.BussinessErrorException;

public interface UserQuery {
    UserResponse byUsername(String username) throws NotFoundErrorException, BussinessErrorException;
}
