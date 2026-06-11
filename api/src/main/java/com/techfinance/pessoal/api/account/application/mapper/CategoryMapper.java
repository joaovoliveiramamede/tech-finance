package com.techfinance.pessoal.api.account.application.mapper;

import org.springframework.stereotype.Component;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.CategoryRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.CategoryResponse;
import com.techfinance.pessoal.api.account.domain.model.Category;
import com.techfinance.pessoal.api.account.domain.port.out.result.CategoryResult;
import com.techfinance.pessoal.api.infra.shared.converter.MapperConverter;

@Component
public class CategoryMapper 
    extends MapperConverter<CategoryRequest, CategoryResponse, CategoryResult, Category> {
    
    @Override
    protected Category doToEntity(CategoryRequest request) {
        return Category.builder()
            .name(request.name())
            .description(request.description())
            .build();
    }

    @Override
    protected CategoryResult doToResult(Category entity) {
        return CategoryResult.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    @Override
    protected CategoryResponse doToResponse(CategoryResult result) {
        return new CategoryResponse(
            result.getId(),
            result.getName(),
            result.getDescription(),
            result.getCreatedAt(),
            result.getUpdatedAt()
        );
    }
}
