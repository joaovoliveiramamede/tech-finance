package com.techfinance.pessoal.api.auth.domain.port.in;

import com.techfinance.pessoal.api.auth.adapter.in.dto.request.LoginRequest;
import com.techfinance.pessoal.api.auth.adapter.in.dto.response.AuthResponse;
import com.techfinance.pessoal.api.infra.exception.BussinessErrorException;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;

public interface AuthCommand {
    void register(UserRequest request) throws BussinessErrorException;
    AuthResponse login(LoginRequest request) throws BussinessErrorException;
}