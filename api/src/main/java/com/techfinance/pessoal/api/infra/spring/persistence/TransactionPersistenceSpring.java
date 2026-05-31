package com.techfinance.pessoal.api.infra.spring.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techfinance.pessoal.api.account.domain.model.Transaction;

public interface TransactionPersistenceSpring extends JpaRepository<Transaction, UUID> {}