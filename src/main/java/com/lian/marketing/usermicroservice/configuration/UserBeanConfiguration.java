package com.lian.marketing.usermicroservice.configuration;

import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.UserUseCase;
import com.lian.marketing.usermicroservice.domain.spi.IUserPersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter.UserAdapter;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper.IUserEntityMapper;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserBeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(
                userPersistencePort()
        );
    }

}
