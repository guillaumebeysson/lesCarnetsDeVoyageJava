package com.carnetdevoyage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain disableSecurity(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity
				.csrf().disable()
				.httpBasic().disable()
				.sessionManagement().disable()
				.logout().disable()
				.formLogin().disable()
				.build();
	}

}
