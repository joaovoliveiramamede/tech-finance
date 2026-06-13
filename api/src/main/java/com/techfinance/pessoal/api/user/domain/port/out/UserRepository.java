package com.techfinance.pessoal.api.user.domain.port.out;

import java.util.Optional;

import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;
import com.techfinance.pessoal.api.user.domain.model.User;

public interface UserRepository {
    Optional<User> findByUsername(String username) throws UnexpectedErrorException;
    User save(User user) throws UnexpectedErrorException;
}
