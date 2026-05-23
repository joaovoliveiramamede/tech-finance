package com.techfinance.pessoal.api.auth.domain.port.in;

import com.techfinance.pessoal.api.auth.adapter.in.dto.request.LoginRequest;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.application.exception.BussinessErrorException;

public interface AuthCommand {
    void register(UserRequest request) throws BussinessErrorException;
    String login(LoginRequest request) throws BussinessErrorException;
}