package com.java.challenge.java_challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/v1/user/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/h2-console/")
                .permitAll()
                .and()
                .headers().frameOptions().disable().and()
                .build();

    }
}
