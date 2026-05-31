package com.techfinance.pessoal.api.infra.spring.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techfinance.pessoal.api.account.domain.model.Account;

public interface AccountPersistenceSpring extends JpaRepository<Account, UUID> {}