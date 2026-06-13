package com.techfinance.pessoal.api.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI techFinanceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TechFinance Pessoal API")
                        .description("API para controle financeiro pessoal")
                        .version("v1")
                        .contact(new Contact()
                                .name("João Vitor")
                                .email("jvoliveiramamede4467@gmail.com"))
                        .license(new License()
                                .name("MIT")));
    }

}
