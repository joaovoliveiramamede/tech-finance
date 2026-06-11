package com.techfinance.pessoal.api.account.application.mapper;

import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.CategoryRequest;
import com.techfinance.pessoal.api.account.domain.model.Category;
import com.techfinance.pessoal.api.account.domain.port.out.result.CategoryResult;
import com.techfinance.pessoal.api.infra.shared.converter.MapperConverter;

@Component
public class CategoryMapper 
    extends MapperConverter<CategoryRequest, CategoryResult, Category> {
    
    @Override
    protected Category doToEntity(CategoryRequest request) {
        return Category.builder()
            .name(request.name())
            .description(request.description())
            .build();
    }

    @Override
    protected CategoryResult doToResponse(Category entity) {
        return new CategoryResult(
            entity.getId(), 
            entity.getName(), 
            entity.getDescription(), 
            entity.getCreatedAt(), 
            entity.getUpdatedAt()
        );
    }
}
