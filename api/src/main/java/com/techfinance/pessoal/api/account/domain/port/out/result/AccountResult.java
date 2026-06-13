package com.techfinance.pessoal.api.account.domain.port.out.result;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.techfinance.pessoal.api.account.domain.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResult {

    private UUID id;
    private UUID userId;
    private String name;
    private BigDecimal balance;
    private AccountType type;
    private Instant createdAt;
    private Instant updatedAt;

}
