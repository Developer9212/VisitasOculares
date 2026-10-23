package com.fenoreste.voculares.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Con java 17
        /*http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/generaHash","/api/v1/documento")
                        .permitAll()
                        // 🔐 LOGIN protegido por Basic
                        .requestMatchers("/auth/login","/api/create_user")
                        .authenticated()

                        // 🔑 APIs usan JWT
                        .requestMatchers("/api/**")
                        .authenticated()

                        .anyRequest()
                        .permitAll()
                )

                // 🔐 BASIC AUTH
                .httpBasic(Customizer.withDefaults())

        // 🔑 JWT
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));*/


        http.csrf().disable()

                .authorizeRequests().antMatchers("/api/generaHash", "/api/v1/documento").permitAll()

                // 🔐 LOGIN protegido por Basic
                .antMatchers("/auth/login", "/api/create_user").authenticated()

                // 🔑 APIs usan JWT
                .antMatchers("/api/**").authenticated()

                .anyRequest().permitAll()

                // 🔐 BASIC AUTH
                .and().httpBasic()

                // 🔑 JWT
                .and().oauth2ResourceServer().jwt();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}