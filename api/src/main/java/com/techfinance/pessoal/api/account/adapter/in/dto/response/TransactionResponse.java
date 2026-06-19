package com.techfinance.pessoal.api.account.adapter.in.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techfinance.pessoal.api.account.domain.enums.TransactionType;
import com.techfinance.pessoal.api.account.domain.port.out.result.TransactionResult;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public record TransactionResponse(

    @JsonProperty(value = "id")
    UUID id,

    @JsonProperty(value = "valor")
    BigDecimal amount,

    @JsonProperty(value = "tipo")
    TransactionType type,

    @JsonProperty(value = "descricao")
    String description,

    @JsonProperty(value = "ocorreu_em")
    Instant occurredAt,

    @JsonProperty(value = "id_conta")
    UUID accountId,

    @JsonProperty(value = "nome_conta")
    String accountName,

    @JsonProperty(value = "id_categoria")
    UUID categoryId,

    @JsonProperty(value = "nome_categoria")
    String categoryName,

    @JsonProperty(value = "data_criacao")
    Instant createdAt,

    @JsonProperty(value = "data_atualizacao")
    Instant updatedAt
) {
    public static TransactionResponse from(TransactionResult result) {
        return new TransactionResponse(
            result.getId(),
            result.getAmount(),
            result.getType(),
            result.getDescription(),
            result.getOccurredAt(),
            result.getAccount() != null ? result.getAccount().getId() : null,
            result.getAccount() != null ? result.getAccount().getName() : null,
            result.getCategory() != null ? result.getCategory().getId() : null,
            result.getCategory() != null ? result.getCategory().getName() : null,
            result.getCreatedAt(),
            result.getUpdatedAt()
        );
    }
}
