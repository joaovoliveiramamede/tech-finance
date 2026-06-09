package com.techfinance.pessoal.api.account.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.enums.AccountType;
import com.techfinance.pessoal.api.infra.shared.entitybase.EntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends EntityBase {

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "saldo", nullable = false)
    private BigDecimal balance;

    @Column(name = "tipo", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountType type;

}