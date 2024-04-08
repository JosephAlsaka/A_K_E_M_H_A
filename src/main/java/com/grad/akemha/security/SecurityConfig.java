package com.grad.akemha.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // this if for role//pre.post, note: you should save role on token
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //csrf is specially token from spring it's important in banking things and when there is a session but in our case we have stateless session so we should do disable
                .authorizeHttpRequests((authReq) -> authReq
                                .requestMatchers(
                                        "/api/auth/**",
                                        "/swagger-ui",
                                        "/swagger-ui.html/**",
                                        "/swagger-ui/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs/**",
                                        "/api/health",
                                        "/api/image/**"
                                )
                                .permitAll() // .authorizeRequests().antMatchers("/api/**", "/h2-console/**").permitAll()
//                        .requestMatchers("/api/product/**").hasRole("OWNER")
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
