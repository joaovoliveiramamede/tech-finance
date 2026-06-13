package com.techfinance.pessoal.api.infra.shared.converter;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class MapperConverter<REQ, RES, RESULT, ENTITY> {

    public ENTITY toEntity(REQ request) {
        log.debug("convertendo {} para entidade", request.getClass().getSimpleName());
        return doToEntity(request);
    }

    public RESULT toResult(ENTITY entity) {
        log.debug("convertendo {} para resultado", entity.getClass().getSimpleName());
        return doToResult(entity);
    }

    public RES toResponse(RESULT result) {
        log.debug("convertendo {} para response", result.getClass().getSimpleName());
        return doToResponse(result);
    }

    protected ENTITY doToEntity(REQ request) {
        throw new UnsupportedOperationException(
            "método doToEntity não implementado para este mapper"
        );
    }

    protected RESULT doToResult(ENTITY entity) {
        throw new UnsupportedOperationException(
            "método doToResult não implementado para este mapper"
        );
    }

    protected RES doToResponse(RESULT result) {
        throw new UnsupportedOperationException(
            "método doToResponse não implementado para este mapper"
        );
    }
}