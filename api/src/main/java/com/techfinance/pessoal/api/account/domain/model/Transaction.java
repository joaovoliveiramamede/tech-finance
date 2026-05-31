package com.techfinance.pessoal.api.account.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
 
    @Id
    private UUID id;

    @Column(name = "quantidade", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao", nullable = false)
    private TransactionType type;

    @Column(name = "descricao")
    private String description;

    @Column(name = "data_transacao", nullable = false, updatable = false)
    private Instant occurredAt;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "data_atualizacao", nullable = false)
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    public void prePersist() {

        if (id == null) {
            this.id = UUID.randomUUID();
        }

        Instant now = Instant.now();

        if (occurredAt == null) {
            occurredAt = now;
        }

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

}