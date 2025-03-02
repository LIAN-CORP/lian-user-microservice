package com.lian.marketing.usermicroservice.configuration;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthBeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IAuthServicePort authService() {
        return new AuthUseCase(passwordEncoder());
    }

}
