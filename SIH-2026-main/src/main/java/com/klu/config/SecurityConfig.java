package com.klu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ==========================================
    // PASSWORD ENCODER
    // ==========================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // ==========================================
    // SECURITY CONFIGURATION
    // ==========================================

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .cors(Customizer.withDefaults())

            // Allow PDF to display inside React
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
            )

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(

                    "/api/materials/**",

                    "/uploads/**",

                    "/api/quizzes/**",

                    "/api/recommendations/**"

                ).permitAll()

                .anyRequest().permitAll()

            );

        return http.build();
    }
}