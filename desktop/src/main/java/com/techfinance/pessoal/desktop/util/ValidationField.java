package com.techfinance.pessoal.desktop.util;

import java.util.Optional;

public final class ValidationField {

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private ValidationField() {}
    
    public static boolean isBlank(String value) {
        return normalize(value).isEmpty();
    }

    public static boolean passwordIsValid(String password) {
        return normalize(password).length() >= MIN_PASSWORD_LENGTH;
    }

    public static boolean nameIsValid(String name) {
        return normalize(name).length() >= MIN_NAME_LENGTH;
    }

    public static boolean usernameIsValid(String username) {
        return !normalize(username).isEmpty();
    }

    public static Optional<String> validateRegister(String name, String username, String password) {
        if (isBlank(name)) {
            return Optional.of("Informe seu nome.");
        }

        if (!nameIsValid(name)) {
            return Optional.of("Nome inválido. Mínimo 3 caracteres.");
        }

        if (isBlank(username)) {
            return Optional.of("Informe o usuário.");
        }

        if (isBlank(password)) {
            return Optional.of("Informe sua senha.");
        }

        if (!passwordIsValid(password)) {
            return Optional.of("Senha inválida. Mínimo 6 caracteres.");
        }

        return Optional.empty();
    }

    public static String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
