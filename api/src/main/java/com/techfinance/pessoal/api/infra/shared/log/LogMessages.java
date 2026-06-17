package com.techfinance.pessoal.api.infra.shared.log;

public final class LogMessages {

    private LogMessages() {}

    public static final String START = "iniciando operacao de {} no contexto {}";
    public static final String START_SAVED_DATABASE = "iniciando operação de {} da entidade {} no banco de dados";

    public static final String FINISH = "finalizando operacao de {} no contexto {}";

    public static final String AFTER_FINISH = "finalizado a operação de {} com sucesso";
    public static final String FINISH_SAVED_DATABASE = "finalizando operação de {} no banco de dados";

    public static final String NOT_NULL_VALIDATION_PROPERTY = "propriedade {} não pode ser nula ou vazia";

    public static final String BUSINESS_ERROR = "erro ao tentar fazer a operacao de {} na classe {}";

    public static final String BUSINESS_ERROR_EXCEPTION_ACCOUNT = "erro ao tentar criar conta";
    public static final String BUSINESS_ERROR_EXCEPTION_CATEGORY = "erro ao tentar criar categoria";
    public static final String BUSINESS_ERROR_EXCEPTION_TRANSACTION = "erro ao tentar criar transação";
    public static final String BUSINESS_ERROR_EXCEPTION_USER = "erro ao processar usuário";
    public static final String BUSINESS_ERROR_EXCEPTION_AUTH = "erro ao processar autenticação";
}
