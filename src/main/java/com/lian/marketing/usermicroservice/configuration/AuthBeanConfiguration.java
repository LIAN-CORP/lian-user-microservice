package com.lian.marketing.usermicroservice.configuration;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.ITokenServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.AuthUseCase;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AuthBeanConfiguration {

    private final UserBeanConfiguration userBeanConfiguration;
    private final ITokenServicePort tokenServicePort;
    private final RegisterUserBeanConfiguration registerUserBeanConfiguration;

    @Bean
    public Argon2 argon2() {
        return Argon2Factory.create();
    }

    @Bean
    public IAuthServicePort authService() {
        return new AuthUseCase(argon2(), userBeanConfiguration.userServicePort(), tokenServicePort, registerUserBeanConfiguration.registerUserPersistencePort());
    }

}
