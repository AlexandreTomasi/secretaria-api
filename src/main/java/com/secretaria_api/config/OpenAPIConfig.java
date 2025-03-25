package com.secretaria_api.config;


import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/api/**") // Define os endpoints que estarão na documentação
                .build();
    }

    @Bean
    public Info apiInfo() {
        return new Info().title("API da Secretaria")
                .description("Documentação da API de serviços da Secretaria")
                .version("1.0.0");
    }
}
