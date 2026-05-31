package com.techfinance.pessoal.api.account.adapter.in.dto.request;

import java.math.BigDecimal;

public record AccountRequest(
    BigDecimal balance
) {}