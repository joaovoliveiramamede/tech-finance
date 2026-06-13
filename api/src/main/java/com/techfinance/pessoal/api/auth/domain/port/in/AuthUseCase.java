package com.techfinance.pessoal.api.auth.domain.port.in;

import com.techfinance.pessoal.api.auth.adapter.in.dto.request.LoginRequest;
import com.techfinance.pessoal.api.auth.adapter.in.dto.request.RegisterRequest;
import com.techfinance.pessoal.api.auth.domain.port.out.result.AuthResult;
import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;

public interface AuthUseCase {
    AuthResult register(RegisterRequest request) throws BusinessErrorException;
    AuthResult login(LoginRequest request) throws BusinessErrorException;
}
