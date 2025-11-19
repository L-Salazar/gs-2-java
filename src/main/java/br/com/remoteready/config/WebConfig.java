package br.com.remoteready.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permite CORS para todas as origens
        registry.addMapping("/**")
                .allowedOrigins("*")  // Permite todas as origens
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*");// Permite enviar credenciais (como cookies, tokens)
    }
}