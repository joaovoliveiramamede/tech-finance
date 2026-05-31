package com.techfinance.pessoal.api.account.domain.port.out;

import java.rmi.UnexpectedException;

import com.techfinance.pessoal.api.account.domain.model.Category;

public interface CategoryRepository {
    Category save(Category entity) throws UnexpectedException;
}