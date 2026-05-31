package com.techfinance.pessoal.api.user.application.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.infra.exception.BussinessErrorException;
import com.techfinance.pessoal.api.infra.security.exception.NotFoundErrorException;
import com.techfinance.pessoal.api.user.adapter.in.dto.request.UserRequest;
import com.techfinance.pessoal.api.user.adapter.in.dto.response.UserResponse;
import com.techfinance.pessoal.api.user.application.mapper.UserMapper;
import com.techfinance.pessoal.api.user.domain.model.User;
import com.techfinance.pessoal.api.user.domain.port.in.UserCommand;
import com.techfinance.pessoal.api.user.domain.port.in.UserQuery;
import com.techfinance.pessoal.api.user.domain.port.out.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService implements 
    UserCommand,
    UserQuery,
    UserDetailsService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public UserResponse create(UserRequest request) throws BussinessErrorException {
        try {
            log.info("iniciando criação do usuário | name={}", request.name());
            User entity = mapper.toEntity(request);

            log.info("criptografando senha de usuário");
            entity.setPassword(
                encoder.encode(request.password())
            );

            User saved = repository.save(entity);
            log.info("usuário salvo com sucesso");

            return mapper.toResponse(saved);
        } catch (Exception exception) {
            throw new BussinessErrorException("erro ao salvar usuário", exception);
        }
    }

    @Override
    public UserResponse byUsername(String username) throws NotFoundErrorException, BussinessErrorException {
        try {
            log.info("iniciando busca do usuário pelo username | username={}", username);
            User found = Optional.ofNullable(repository.findByUsername(username))
                .orElseThrow(() -> new NotFoundErrorException("usuário não encontrado com esse username | username=" + username));
            log.info("usuário encontrado");
        return mapper.toResponse(found);
        } catch (NotFoundErrorException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new BussinessErrorException("erro ao buscar usuário", exception);
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("iniciando o carregamento do usuário pelo username para login | username={}", username);

        User user = Optional.ofNullable(repository.findByUsername(username))
            .orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado com esse username | username=" + username));

            return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

}
