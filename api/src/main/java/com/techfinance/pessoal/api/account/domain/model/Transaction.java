package com.techfinance.pessoal.api.account.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

import com.techfinance.pessoal.api.account.domain.enums.TransactionType;

import com.techfinance.pessoal.api.infra.shared.entitybase.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transacoes")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction extends EntityBase {

    @Column(name = "valor", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao", nullable = false)
    private TransactionType type;

    @Column(name = "descricao")
    private String description;

    @Column(name = "data_transacao", nullable = false, updatable = false)
    private Instant occurredAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}