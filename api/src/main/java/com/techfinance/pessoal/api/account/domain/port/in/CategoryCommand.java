package com.techfinance.pessoal.api.account.domain.port.in;

import com.techfinance.pessoal.api.account.adapter.in.dto.request.CategoryRequest;
import com.techfinance.pessoal.api.account.domain.port.out.result.CategoryResult;
import com.techfinance.pessoal.api.infra.exception.UnexpectedErrorException;

public interface CategoryCommand {
    CategoryResult create(CategoryRequest request) throws UnexpectedErrorException;

}
