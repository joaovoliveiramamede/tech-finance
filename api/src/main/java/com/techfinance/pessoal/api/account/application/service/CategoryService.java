package com.techfinance.pessoal.api.account.application.service;

import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.CategoryRequest;
import com.techfinance.pessoal.api.account.application.mapper.CategoryMapper;
import com.techfinance.pessoal.api.account.domain.model.Category;
import com.techfinance.pessoal.api.account.domain.port.in.CategoryCommand;
import com.techfinance.pessoal.api.account.domain.port.out.CategoryRepository;
import com.techfinance.pessoal.api.account.domain.port.out.result.CategoryResult;
import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;
import com.techfinance.pessoal.api.infra.shared.log.LogMessages;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryService implements CategoryCommand {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public CategoryResult create(CategoryRequest request) throws BusinessErrorException {
        try {
            log.debug(LogMessages.START, "criação", "categoria");
            log.info("criando categoria | categoryName={}", request.name());

            Category entity = mapper.toEntity(request);
            Category saved = repository.save(entity);

            log.info("categoria criada com sucesso | categoryId={}", saved.getId());
            log.debug(LogMessages.FINISH, "criação", "categoria");

            return mapper.toResult(saved);
        } catch (Exception exception) {
            log.error(LogMessages.BUSINESS_ERROR, "criação", Category.class.getSimpleName());
            throw new BusinessErrorException(LogMessages.BUSINESS_ERROR_EXCEPTION_CATEGORY, exception);
        }
    }
}
