package com.techfinance.pessoal.api.user.domain.port.in;

import com.techfinance.pessoal.api.infra.exception.BussinessErrorException;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.adapter.in.dto.response.UserResponse;

public interface UserCommand {
    UserResponse create(UserRequest request) throws BussinessErrorException;
}