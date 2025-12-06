package com.example.bidwar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {
    // stoping authentication for developer mode testing
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf->csrf.disable())
//                .authorizeHttpRequests(auth->auth.anyRequest().permitAll());
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for Postman/API testing
                .authorizeHttpRequests(auth -> auth
                        // 1. PUBLIC ACCESS
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll() // Swagger
                        .requestMatchers(HttpMethod.GET, "/api/auction/*/image").permitAll() // Images
                        .requestMatchers(HttpMethod.GET, "/api/auction/active").permitAll() // List Active Items
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll() // Registration (We will build this next!)

                        // 2. ADMIN ONLY
                        .requestMatchers(HttpMethod.POST, "/api/auction/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/auction/*/image").hasRole("ADMIN")

                        // 3. USER ONLY
                        .requestMatchers(HttpMethod.PATCH, "/api/auction/*/bid").hasRole("USER")

                        // 4. EVERYTHING ELSE
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Use Basic Auth (Username/Password popup)

        return http.build();
    }


}
