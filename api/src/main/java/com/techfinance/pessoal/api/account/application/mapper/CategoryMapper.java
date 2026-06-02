package com.techfinance.pessoal.api.account.application.mapper;

import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.CategoryRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.CategoryResponse;
import com.techfinance.pessoal.api.account.domain.model.Category;
import com.techfinance.pessoal.api.infra.shared.MapperConverter;

@Component
public class CategoryMapper 
    extends MapperConverter<CategoryRequest, CategoryResponse, Category> {
    
    @Override
    protected Category doToEntity(CategoryRequest request) {
        return Category.builder()
            .name(request.name())
            .description(request.description())
            .build();
    }

    @Override
    protected CategoryResponse doToResponse(Category entity) {
        return new CategoryResponse(
            entity.getId(), 
            entity.getName(), 
            entity.getDescription(), 
            entity.getCreatedAt(), 
            entity.getUpdatedAt()
        );
    }
}