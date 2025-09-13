package com.lian.marketing.usermicroservice.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Security;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .formLogin(form -> form.disable())
      .httpBasic(httpBasic -> httpBasic.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/auth/**", "/.well-known/jwks.json").permitAll()
        .anyRequest().authenticated()
      )
      .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  static {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
  }
}
