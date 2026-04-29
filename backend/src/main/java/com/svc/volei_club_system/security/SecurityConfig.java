package com.svc.volei_club_system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // =========================
    // PASSWORD ENCODER (OBRIGATÓRIO)
    // =========================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    // =========================
    // SECURITY CONFIG
    // =========================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

            // =========================
            // CORS
            // =========================

            .cors(cors -> cors.configurationSource(request -> {

                CorsConfiguration config =
                        new CorsConfiguration();

                config.setAllowedOrigins(List.of(
                        "http://localhost:5173"
                ));

                config.setAllowedMethods(List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                ));

                config.setAllowedHeaders(List.of(
                        "*"
                ));

                config.setAllowCredentials(true);

                return config;

            }))

            // =========================
            // CSRF
            // =========================

            .csrf(csrf -> csrf.disable())

            // =========================
            // SESSION
            // =========================

            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

            // =========================
            // ROTAS LIBERADAS
            // =========================

            .authorizeHttpRequests(auth -> auth

                    // login
                    .requestMatchers(
                            "/auth/**"
                    ).permitAll()

                    // cadastro público
                    .requestMatchers(
                            "/atletas/cadastro-completo"
                    ).permitAll()

                    // OPTIONS (preflight)
                    .requestMatchers(
                            "/**"
                    ).permitAll()

                    .anyRequest().authenticated()

            );

        return http.build();

    }

}