package com.techfinance.pessoal.api.infra.shared.converter;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class MapperConverter<REQ, RES, ENTITY> {
    
    public final ENTITY toEntity(REQ request) {
        log.debug("convertendo {} para entidade", request.getClass().getSimpleName());
        return doToEntity(request);
    }

    public final RES toResponse(ENTITY entity) {
        log.debug("convertendo {} para response", entity.getClass().getSimpleName());
        return doToResponse(entity);
    }

    protected abstract ENTITY doToEntity(REQ request);

    protected abstract RES doToResponse(ENTITY entity);
}