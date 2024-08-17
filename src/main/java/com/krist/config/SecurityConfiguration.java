package com.krist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.krist.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RequestMatcher authenticationRequestMatcher;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
            RequestMatcher authenticationRequestMatcher) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationRequestMatcher = authenticationRequestMatcher;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        // Define which requests should be permitted without authentication
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(authenticationRequestMatcher).permitAll().anyRequest().authenticated());

        // Add the JWT authentication filter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000")
                        .allowedMethods("*").allowCredentials(true);
            }
        };
    }
}
