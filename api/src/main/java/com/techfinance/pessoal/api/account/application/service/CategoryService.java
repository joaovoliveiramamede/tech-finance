package com.techfinance.pessoal.api.account.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.CategoryRequest;
import com.techfinance.pessoal.api.account.application.mapper.CategoryMapper;
import com.techfinance.pessoal.api.account.domain.model.Category;
import com.techfinance.pessoal.api.account.domain.port.in.CategoryUseCase;
import com.techfinance.pessoal.api.account.domain.port.out.CategoryRepository;
import com.techfinance.pessoal.api.account.domain.port.out.result.CategoryResult;
import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;
import com.techfinance.pessoal.api.infra.security.context.AuthenticatedUserContext;
import com.techfinance.pessoal.api.infra.shared.log.LogMessages;
import com.techfinance.pessoal.api.user.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Override
    public CategoryResult create(CategoryRequest request) throws BusinessErrorException {
        try {
            User user = authenticatedUserContext.getCurrentUser();

            log.debug(LogMessages.START, "criação", "categoria");
            log.info("criando categoria | categoryName={} | userId={}", request.name(), user.getId());

            Category entity = mapper.toEntity(request);
            entity.setUser(user);

            Category saved = repository.save(entity);

            log.info("categoria criada com sucesso | categoryId={}", saved.getId());
            log.debug(LogMessages.FINISH, "criação", "categoria");

            return mapper.toResult(saved);
        } catch (Exception exception) {
            log.error(LogMessages.BUSINESS_ERROR, "criação", Category.class.getSimpleName());
            throw new BusinessErrorException(LogMessages.BUSINESS_ERROR_EXCEPTION_CATEGORY, exception);
        }
    }

    @Override
    public List<CategoryResult> findAll() throws BusinessErrorException {
        try {
            var userId = authenticatedUserContext.getCurrentUserId();

            log.debug(LogMessages.START, "listagem", "categoria");
            log.info("listando categorias | userId={}", userId);

            List<CategoryResult> results = repository.findAllByUserId(userId).stream()
                .map(mapper::toResult)
                .toList();

            log.debug(LogMessages.FINISH, "listagem", "categoria");
            return results;
        } catch (Exception exception) {
            log.error(LogMessages.BUSINESS_ERROR, "listagem", Category.class.getSimpleName());
            throw new BusinessErrorException(LogMessages.BUSINESS_ERROR_EXCEPTION_CATEGORY, exception);
        }
    }
}
