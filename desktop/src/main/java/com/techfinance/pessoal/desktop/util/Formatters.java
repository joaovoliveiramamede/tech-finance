package com.techfinance.pessoal.desktop.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Formatters {

    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(
        new Locale("pt", "BR")
    );

    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter
        .ofPattern("dd/MM/yyyy HH:mm")
        .withZone(ZoneId.of("America/Sao_Paulo"));

    private static final DateTimeFormatter TRANSACTION_API = DateTimeFormatter
        .ofPattern("dd-MM-yyyy : HH:mm")
        .withZone(ZoneId.of("America/Sao_Paulo"));

    private Formatters() {}

    public static String currency(BigDecimal value) {
        if (value == null) {
            return CURRENCY.format(BigDecimal.ZERO);
        }

        return CURRENCY.format(value);
    }

    public static String dateTime(Instant instant) {
        if (instant == null) {
            return "-";
        }

        return DATE_TIME.format(instant);
    }

    public static String transactionApiDate(Instant instant) {
        return TRANSACTION_API.format(instant != null ? instant : Instant.now());
    }
}
