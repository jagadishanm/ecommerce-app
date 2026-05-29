package com.example.eCommerceApp.e_Commerce.Application.config;

import com.example.eCommerceApp.e_Commerce.Application.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                /*
                    Disable CSRF
                */
                .csrf(csrf -> csrf.disable())

                /*
                    Route permissions
                */
                .authorizeHttpRequests(auth -> auth

                        /*
                            Public APIs
                        */
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        /*
                            Everything else secured
                        */
                        .anyRequest()
                        .authenticated()
                )

                /*
                    Stateless session
                */
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                /*
                    Authentication provider
                */
                .authenticationProvider(authenticationProvider)

                /*
                    JWT filter before username/password filter
                */
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}