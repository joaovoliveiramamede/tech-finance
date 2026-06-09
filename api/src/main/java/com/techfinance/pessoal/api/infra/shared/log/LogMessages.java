package com.techfinance.pessoal.api.infra.shared.log;

public final class LogMessages {

    private LogMessages() {}

    /** Start de Operações  */
    public static final String START = "iniciando operacao de {} no contexto {}";
    public static final String START_SAVED_DATABASE = "iniciando operação de {} da entidade {} no banco de dados";

    /** Finish de Operação */ 
    public static final String FINISH = "finalizando operacao de {} no contexto {}";
    public static final String FINISH_SAVED_DATABASE = "finalizando operação de {} no banco de dados";

    public static final String NOT_NULL_VALIDATION_PROPERTY = "propriedade {} não pode ser nula ou vazia";


    public static final String BUSSINESS_ERROR = "erro ao tentar fazer a operacao de {} na classe {}";

    /** Exception de Operações */
    public static final String BUSSINESS_ERROR_EXCEPTION_CATEGORY = "erro ao tentar criar criar categoria";
}