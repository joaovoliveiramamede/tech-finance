package com.techfinance.pessoal.desktop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryRequest(

    @JsonProperty("nome")
    String name,

    @JsonProperty("descricao")
    String description
) {}
