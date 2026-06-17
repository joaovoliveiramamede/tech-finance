package com.techfinance.pessoal.api.user.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;
import com.techfinance.pessoal.api.infra.exception.NotFoundErrorException;
import com.techfinance.pessoal.api.infra.shared.log.LogMessages;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.application.mapper.UserMapper;
import com.techfinance.pessoal.api.user.domain.model.User;
import com.techfinance.pessoal.api.user.domain.port.in.UserUseCase;
import com.techfinance.pessoal.api.user.domain.port.out.UserRepository;
import com.techfinance.pessoal.api.user.domain.port.out.result.UserResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public UserResult create(UserRequest request) throws BusinessErrorException {
        try {
            log.debug(LogMessages.START, "criação", "usuário");
            log.info("criando usuário | username={}", request.username());

            User entity = mapper.toEntity(request);
            entity.setPassword(encoder.encode(request.password()));

            User saved = repository.save(entity);
            log.info("usuário criado com sucesso | userId={}", saved.getId());
            log.debug(LogMessages.FINISH, "criação", "usuário");

            UserResult result = mapper.toResult(saved);
            log.info(LogMessages.AFTER_FINISH, "criacao");
            return result;
        } catch (Exception exception) {
            log.error(LogMessages.BUSINESS_ERROR, "criação", User.class.getSimpleName());
            throw new BusinessErrorException(LogMessages.BUSINESS_ERROR_EXCEPTION_USER, exception);
        }
    }

    @Override
    public UserResult byUsername(String username) throws NotFoundErrorException, BusinessErrorException {
        try {
            log.debug(LogMessages.START, "busca", "usuário");
            log.info("buscando usuário | username={}", username);

            User found = repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundErrorException(
                    "usuário não encontrado com esse username | username=" + username));

            log.debug(LogMessages.FINISH, "busca", "usuário");
            return mapper.toResult(found);
        } catch (NotFoundErrorException exception) {
            throw exception;
        } catch (Exception exception) {
            log.error(LogMessages.BUSINESS_ERROR, "busca", User.class.getSimpleName());
            throw new BusinessErrorException(LogMessages.BUSINESS_ERROR_EXCEPTION_USER, exception);
        }
    }
}
