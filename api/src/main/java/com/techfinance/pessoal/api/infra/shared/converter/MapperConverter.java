package com.techfinance.pessoal.api.infra.shared.converter;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class MapperConverter<REQ, RES, RESULT, ENTITY> {

    public final ENTITY toEntity(REQ request) {
        log.debug("convertendo {} para entidade", request.getClass().getSimpleName());
        return doToEntity(request);
    }

    public final RESULT toResult(ENTITY entity) {
        log.debug("convertendo {} para resultado", entity.getClass().getSimpleName());
        return doToResult(entity);
    }

    public final RES toResponse(RESULT result) {
        log.debug("convertendo {} para response", result.getClass().getSimpleName());
        return doToResponse(result);
    }

    protected abstract ENTITY doToEntity(REQ request);
    protected abstract RES doToResponse(RESULT result);
    protected abstract RESULT doToResult(ENTITY entity);
}
