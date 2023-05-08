package com.carnetdevoyage.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
	
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    SecurityFilterChain disableSecurity(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().disable()
            .logout().disable()
            .formLogin().disable()
            .build();
    }
}