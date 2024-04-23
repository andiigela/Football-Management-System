package com.football.dev.footballapp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
        registry.addMapping("/images/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET")  // Allow only GET requests for image resources
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
