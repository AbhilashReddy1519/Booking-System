package com.app.bs.booking_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.bs.booking_system.modules.users.Role;
import com.app.bs.booking_system.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  private final JwtAuthFilter jwtAuthFilter;

  SecurityConfig(JwtAuthFilter jwtAuthFilter) {
    this.jwtAuthFilter = jwtAuthFilter;
  }

  @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/movie/**", "/show/**", "/theater/**").permitAll()
            .requestMatchers("/users/create", "/users/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/movie", "/theater", "/screen", "/show").hasRole(Role.THEATER_OWNER.toString())
            .requestMatchers("/booking/**", "/payment/**").hasAnyRole(Role.ADMIN.toString(), Role.CUSTOMER.toString(), Role.THEATER_OWNER.toString())
            .anyRequest().authenticated())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
