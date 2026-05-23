package com.techfinance.pessoal.api.infra.spring.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techfinance.pessoal.api.user.domain.model.User;

public interface UserPersistenceSpring extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}