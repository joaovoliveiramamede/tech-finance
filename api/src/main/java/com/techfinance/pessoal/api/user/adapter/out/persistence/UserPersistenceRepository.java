package com.techfinance.pessoal.api.user.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;
import com.techfinance.pessoal.api.infra.spring.persistence.UserPersistenceSpring;
import com.techfinance.pessoal.api.user.domain.model.User;
import com.techfinance.pessoal.api.user.domain.port.out.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserPersistenceRepository implements UserRepository {

    private final UserPersistenceSpring repository;

    @Override
    public Optional<User> findByUsername(String username) throws UnexpectedErrorException {
        try {
            log.info("buscando usuário pelo username | username={}", username);
            return repository.findByUsername(username);
        } catch (Exception exception) {
            log.error("erro ao buscar usuário no banco de dados | username={}", username);
            throw new UnexpectedErrorException("erro ao buscar usuário no banco de dados", exception);
        }
    }

    @Override
    public User save(User user) throws UnexpectedErrorException {
        try {
            log.info("salvando usuário no banco de dados");
            return repository.save(user);
        } catch (Exception exception) {
            log.error("erro ao salvar usuário no banco de dados");
            throw new UnexpectedErrorException("erro ao salvar usuário no banco de dados", exception);
        }
    }
}
