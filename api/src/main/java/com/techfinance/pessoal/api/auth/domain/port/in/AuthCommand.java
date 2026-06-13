package com.techfinance.pessoal.api.auth.domain.port.in;

import com.techfinance.pessoal.api.auth.adapter.in.dto.request.LoginRequest;
import com.techfinance.pessoal.api.auth.adapter.in.dto.request.RegisterRequest;
import com.techfinance.pessoal.api.auth.adapter.in.dto.response.AuthResponse;
import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;

public interface AuthCommand {
    void register(RegisterRequest request) throws BusinessErrorException;
    AuthResponse login(LoginRequest request) throws BusinessErrorException;
}
