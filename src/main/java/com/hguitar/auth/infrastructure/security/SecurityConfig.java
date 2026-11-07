package com.hguitar.auth.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/problem+json");
                    response.getWriter().write(
                        "{\"status\":401,\"title\":\"Unauthorized\",\"detail\":\"Authentication required\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/problem+json");
                    response.getWriter().write(
                        "{\"status\":403,\"title\":\"Forbidden\",\"detail\":\"Access denied\"}");
                })
            )
            .authorizeHttpRequests(auth -> auth
                // API docs — public
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/api-docs/**").permitAll()
                // Auth endpoints — public
                .requestMatchers("/api/v1/auth/**").permitAll()
                // Read-only catalog — public (students and guests can browse)
                .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/modules/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/lessons/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/chords/**").permitAll()
                // Students may mark their own lessons complete
                .requestMatchers(HttpMethod.POST, "/api/v1/lessons/*/complete").authenticated()
                // Lesson + content management — CONTENT_EDITOR or ADMIN only
                .requestMatchers(HttpMethod.POST,   "/api/v1/lessons/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/v1/lessons/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH,  "/api/v1/lessons/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/lessons/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                // Lesson blocks use /api/v1/lessons/{lessonId}/blocks/**
                .requestMatchers(HttpMethod.POST,   "/api/v1/lessons/*/blocks/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/v1/lessons/*/blocks/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH,  "/api/v1/lessons/*/blocks/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/lessons/*/blocks/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                // Course + module management
                .requestMatchers(HttpMethod.POST,   "/api/v1/courses/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/v1/courses/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH,  "/api/v1/courses/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.POST,   "/api/v1/modules/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/v1/modules/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH,  "/api/v1/modules/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/modules/**").hasAnyRole("CONTENT_EDITOR", "ADMIN")
                // Admin routes
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                // Everything else requires authentication
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(allowedOrigins.split(",")));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
