package com.techfinance.pessoal.api.infra.spring.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techfinance.pessoal.api.account.domain.model.Category;

public interface CategoryPersistenceSpring extends JpaRepository<Category, UUID> {

    Optional<Category> findByIdAndUser_Id(UUID id, UUID userId);

    List<Category> findByUser_IdOrderByCreatedAtDesc(UUID userId);
}
