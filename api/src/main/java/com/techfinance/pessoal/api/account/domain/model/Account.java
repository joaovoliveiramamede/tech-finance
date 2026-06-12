package com.techfinance.pessoal.api.account.domain.model;

import java.math.BigDecimal;

import com.techfinance.pessoal.api.account.domain.enums.AccountType;
import com.techfinance.pessoal.api.account.domain.enums.TransactionType;
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

    public void applyTransaction(Transaction transaction) {
        if (transaction.getType() == TransactionType.INCOME) {
            credit(transaction.getAmount());
            return;
        }

        debit(transaction.getAmount());
    }

    private void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    private void debit(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("saldo insuficiente para realizar a transacao");
        }

        this.balance = this.balance.subtract(amount);
    }

}
