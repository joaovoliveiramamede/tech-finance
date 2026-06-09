package com.techfinance.pessoal.api.account.domain.model;

import java.time.Instant;
import java.util.UUID;

import com.techfinance.pessoal.api.infra.shared.entitybase.EntityBase;
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
public class Category extends EntityBase {

    @Column(name = "nome", nullable = false, unique = true)
    private String name;

    @Column(name = "descricao", nullable = false)
    private String description;

}