package com.techfinance.pessoal.api.account.domain.port.in;

import java.util.List;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.CategoryRequest;
import com.techfinance.pessoal.api.account.domain.port.out.result.CategoryResult;
import com.techfinance.pessoal.api.infra.exception.BusinessErrorException;

public interface CategoryUseCase {
    CategoryResult create(CategoryRequest request) throws BusinessErrorException;
    List<CategoryResult> findAll() throws BusinessErrorException;
}
