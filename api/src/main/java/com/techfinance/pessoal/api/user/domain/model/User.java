package com.techfinance.pessoal.api.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.techfinance.pessoal.api.user.domain.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import com.techfinance.pessoal.api.infra.shared.entitybase.EntityBase;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends EntityBase {

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "usuario", unique = true, nullable = false)
    private String username;

    @Column(name = "senha", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false)
    private Role role;

}