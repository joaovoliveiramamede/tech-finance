package com.techfinance.pessoal.api.account.domain.model;

import com.techfinance.pessoal.api.infra.shared.entitybase.EntityBase;
import com.techfinance.pessoal.api.user.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "categorias",
    uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "nome"})
)
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category extends EntityBase {

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "descricao", nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;
}
