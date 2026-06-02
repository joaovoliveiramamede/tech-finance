package com.techfinance.pessoal.api.account.domain.port.in;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.CategoryRequest;
import com.techfinance.pessoal.api.account.adapter.in.dto.response.CategoryResponse;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;

public interface CategoryCommand {
    CategoryResponse create(CategoryRequest request) throws UnexpectedErrorException;

}