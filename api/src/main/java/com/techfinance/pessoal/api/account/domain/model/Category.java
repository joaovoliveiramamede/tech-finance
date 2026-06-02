package com.techfinance.pessoal.api.account.domain.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "descricao", nullable = false)
    private String description;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "data_atualizacao", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {

        if (id == null) {
            this.id = UUID.randomUUID();
        }

        Instant now = Instant.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}