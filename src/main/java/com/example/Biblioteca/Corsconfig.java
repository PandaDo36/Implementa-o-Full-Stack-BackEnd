package com.example.Biblioteca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Corsconfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Permite apenas rotas que começam com /api/
                        .allowedOrigins("http://localhost:3000") // Permite o frontend local
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("Content-Type", "Authorization", "X-Requested-With") // Cabeçalhos permitidos
                        .allowCredentials(true); // Permite envio de cookies/autenticação
            }
        };
    }
}