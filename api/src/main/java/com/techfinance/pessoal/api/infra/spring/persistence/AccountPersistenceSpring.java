package com.techfinance.pessoal.api.infra.spring.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techfinance.pessoal.api.account.domain.model.Account;

public interface AccountPersistenceSpring extends JpaRepository<Account, UUID> {

    Optional<Account> findByIdAndUser_Id(UUID id, UUID userId);

    List<Account> findByUser_IdOrderByCreatedAtDesc(UUID userId);
}
