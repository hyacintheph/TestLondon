package com.plant.managment.security.impl;


import com.plant.managment.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    // set the filter for Oauth
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .userDetailsService(userService)
                // set all permit routes
                .authorizeHttpRequests(authRequest -> authRequest.requestMatchers(
                        "/api/v1/users/create",
                        "/api/v1/users/login").permitAll())
                //set authenticated routes
                .authorizeHttpRequests(authRequest ->
                        authRequest.requestMatchers("/api/v1/users/logout",
                                "/api/v1/users/auth",
                                "/api/v1/plants",
                                "/api/v1/plants/plantNeed",
                                "/api/v1/plants/watered").authenticated()
                )
                .build();
    }



}
