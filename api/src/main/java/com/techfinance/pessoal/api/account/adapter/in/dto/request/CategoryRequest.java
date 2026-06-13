package com.techfinance.pessoal.api.account.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techfinance.pessoal.api.infra.shared.validation.annotations.RequiredField;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CategoryRequest(

    @JsonProperty(value = "nome")
    @RequiredField(property = "nome")
    String name,

    @JsonProperty(value = "descricao")
    @RequiredField(property = "descricao")
    String description
) {}
