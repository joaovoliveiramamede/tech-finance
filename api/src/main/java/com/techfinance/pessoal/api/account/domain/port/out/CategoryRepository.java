package com.techfinance.pessoal.api.account.domain.port.out;

import java.util.Optional;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.model.Category;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;

public interface CategoryRepository {
    Category save(Category entity) throws UnexpectedErrorException;
    Optional<Category> findById(UUID id) throws UnexpectedErrorException;
}
