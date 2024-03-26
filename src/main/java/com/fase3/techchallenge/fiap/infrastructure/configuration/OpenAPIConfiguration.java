package com.fase3.techchallenge.fiap.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI myOpenAPI() {

        Info info = new Info()
                .title("API RESTAURANTE")
                .version("1.0.0")
                .description("API restaurante");

        return new OpenAPI().info(info);
    }
}