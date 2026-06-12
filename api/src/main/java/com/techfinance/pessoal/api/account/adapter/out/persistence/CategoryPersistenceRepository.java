package com.techfinance.pessoal.api.account.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.account.domain.model.Category;
import com.techfinance.pessoal.api.account.domain.port.out.CategoryRepository;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;
import com.techfinance.pessoal.api.infra.spring.persistence.CategoryPersistenceSpring;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryPersistenceRepository implements CategoryRepository {
    
    private final CategoryPersistenceSpring repository;

    @Override
    public Category save(Category entity) throws UnexpectedErrorException {
        try {
            log.info("salvando categoria | categoryName={}", entity.getName());
            return repository.save(entity);
        } catch (Exception exception) {
            throw new UnexpectedErrorException("erro ao salvar categoria");
        }
    }

    @Override
    public Optional<Category> findById(UUID id) throws UnexpectedErrorException {
        try {
            log.info("buscando categoria no banco de dados | categoryId={}", id);
            return repository.findById(id);
        } catch (Exception exception) {
            log.error("erro ao buscar categoria no banco de dados | categoryId={}", id);
            throw new UnexpectedErrorException("erro ao buscar categoria no banco de dados", exception);
        }
    }

}
