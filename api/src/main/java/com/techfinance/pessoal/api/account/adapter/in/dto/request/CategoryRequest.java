package com.techfinance.pessoal.api.account.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CategoryRequest(

    @JsonProperty(value = "nome")
    @NotBlank(message = "propriedade nome não pode ser nula ou vazia")
    String name,

    @JsonProperty(value = "descricao")
    @NotBlank(message = "propriedade descricao não pode ser nula ou vazia")
    String description
) {}