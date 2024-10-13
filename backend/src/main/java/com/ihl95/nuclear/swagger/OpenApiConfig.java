package com.ihl95.nuclear.swagger;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi mainApi() {
        return GroupedOpenApi.builder()
            .group("main") // Nombre del grupo para la API principal
            .pathsToExclude("/api/auth/**") // Excluirá los endpoints de autenticación
            .build();
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
            .group("auth") // Nombre del grupo para la API de autenticación
            .pathsToMatch("/api/auth/**") // Incluirá solo los endpoints de autenticación
            .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("API de Nuclear Plants").version("1.0.0")
            .description("Documentación de la API de Nuclear Plants, incluyendo autenticación."))
            .addSecurityItem(new SecurityRequirement().addList("JWT"))
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes("JWT", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Ingresa tu token JWT en formato 'Bearer <token>'")));
    }
}

