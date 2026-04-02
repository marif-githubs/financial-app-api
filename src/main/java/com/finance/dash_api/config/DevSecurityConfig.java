package com.finance.dash_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
public class DevSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disables CSRF protection for dev profile
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permits all requests in dev environment
                );
        return http.build();
    }
}
