package com.kirac.randevusistemi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME =
            "Bearer Authentication";

    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme securityScheme =
                new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT");

        SecurityRequirement securityRequirement =
                new SecurityRequirement()
                        .addList(SECURITY_SCHEME_NAME);

        return new OpenAPI()
                .info(new Info()
                        .title("Randevu Sistemi API")
                        .version("1.0")
                        .description(
                                "Spring Boot kullanılarak geliştirilen randevu sistemi REST API dokümantasyonudur.")
                        .contact(new Contact()
                                .name("Batur Kıraç Yücedağ")))
                .components(new Components()
                        .addSecuritySchemes(
                                SECURITY_SCHEME_NAME,
                                securityScheme))
                .addSecurityItem(securityRequirement);
    }
}