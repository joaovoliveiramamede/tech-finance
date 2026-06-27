package com.techfinance.pessoal.api.infra.shared.entitybase;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

@Getter
@MappedSuperclass
public abstract class EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    UUID id;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "data_atualizacao", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void prePersist() {
        Instant now = Instant.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void preUpdate() {
        updatedAt = Instant.now();
    }
}
