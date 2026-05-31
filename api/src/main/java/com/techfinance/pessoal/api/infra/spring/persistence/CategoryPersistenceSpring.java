package com.techfinance.pessoal.api.infra.spring.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techfinance.pessoal.api.account.domain.model.Category;

public interface CategoryPersistenceSpring extends JpaRepository<Category, UUID> {}