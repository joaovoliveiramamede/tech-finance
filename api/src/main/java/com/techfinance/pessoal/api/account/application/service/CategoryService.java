package com.techfinance.pessoal.api.account.application.service;

import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.CategoryRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.CategoryResponse;
import com.techfinance.pessoal.api.account.application.mapper.CategoryMapper;
import com.techfinance.pessoal.api.account.domain.model.Category;
import com.techfinance.pessoal.api.account.domain.port.in.CategoryCommand;
import com.techfinance.pessoal.api.account.domain.port.out.CategoryRepository;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryService
    implements CategoryCommand {
    
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public CategoryResponse create(CategoryRequest request) throws UnexpectedErrorException {
        try {
            log.info("criando categoria | categoryName={}", request.name());
            Category entity = mapper.toEntity(request);

            Category saved = repository.save(entity);

            log.info("categoria criada com sucesso | categoryId={}", saved.getId());
            return mapper.toResponse(saved);
        } catch (Exception exception) {
            log.error("erro ao criar categoria | message={}", exception.getMessage());
            throw new UnexpectedErrorException("erro ao criar categoria", exception);
        }
    }

}
