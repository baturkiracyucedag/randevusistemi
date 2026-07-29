package com.kirac.randevusistemi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Berber Randevu Sistemi API")
                        .version("1.0")
                        .description("Spring Boot kullanılarak geliştirilen berber randevu sistemi REST API dokümantasyonudur.")
                        .contact(new Contact()
                                .name("Batur Kıraç Yücedağ")
                        )
                );
    }
}