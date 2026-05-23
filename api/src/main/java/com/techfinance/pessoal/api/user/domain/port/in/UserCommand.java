package com.techfinance.pessoal.api.user.domain.port.in;

import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.adapter.in.dto.response.UserResponse;
import com.techfinance.pessoal.api.user.application.exception.BussinessErrorException;

public interface UserCommand {
    UserResponse create(UserRequest request) throws BussinessErrorException;
}