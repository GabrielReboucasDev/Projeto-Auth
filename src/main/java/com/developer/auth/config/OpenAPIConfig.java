package com.developer.auth.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Autenticação",
                version = "1.0",
                description = "Documentação da API de Autenticação",
                contact = @Contact(name = "GabrielReboucaDev", email = "Informações no perfil do Git!")
        )
)
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("VERSÂO-PADRÃO")
                .pathsToMatch("/**")
                .build();
    }
}