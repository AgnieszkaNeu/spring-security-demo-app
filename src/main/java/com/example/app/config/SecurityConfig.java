package com.example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/register").permitAll()
                    .requestMatchers("/authorities").permitAll()
                    .requestMatchers("/adminPanel").hasRole("ADMIN")
                    .requestMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated()
        ).formLogin(withDefaults())
        .csrf(config -> config.ignoringRequestMatchers("/h2-console/**"))
        .headers(headers -> headers.frameOptions().disable());;

        return http.build();
    }
}
