package com.techfinance.pessoal.api.user.adapter.out.persistence;

import com.techfinance.pessoal.api.infra.spring.persistence.UserPersistenceSpring;
import com.techfinance.pessoal.api.user.application.exception.UnexpectedErrorException;
import com.techfinance.pessoal.api.user.domain.model.User;
import com.techfinance.pessoal.api.user.domain.port.out.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserPersistenceRepostirory implements UserRepository {

    private final UserPersistenceSpring repository;

    @Override
    public User findByUsername(String username) throws UnexpectedErrorException {
        log.info("iniciando busca do usuário pelo username | username={}", username);
        return repository.findByUsername(username)
            .orElseThrow(() -> new UnexpectedErrorException("erro ao buscar o usuário no banco de dados"));
    }

    @Override
    public User save(User user) throws UnexpectedErrorException {
        log.info("iniciando a inserção do usuário no banco de dados");
        return Optional.of(repository.save(user))
            .orElseThrow(() -> new UnexpectedErrorException("erro ao salvar o usuário no banco de dados"));
    }

}
