package com.carnetdevoyage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

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

    @Bean
	RouterFunction<ServerResponse> staticResourceLocator(Environment env) {
		return RouterFunctions.resources("/files/**", new FileSystemResource(env.getProperty("images.path")));
	}
    
}
    
