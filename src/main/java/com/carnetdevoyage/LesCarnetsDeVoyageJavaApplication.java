package com.carnetdevoyage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.carnetdevoyage.config.RsaKeyProperties;

import lombok.AllArgsConstructor;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class LesCarnetsDeVoyageJavaApplication{
	
	
	@Value("${frontend.url}")
	private String frontendUrl;

	public static void main(String[] args) {
		SpringApplication.run(LesCarnetsDeVoyageJavaApplication.class, args);
	}

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "UPDATE", "DELETE", "OPTIONS")
                        .allowedOrigins(frontendUrl);
            }
        };
    }
    }
    
