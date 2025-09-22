package com.lian.marketing.usermicroservice.configuration;

import com.lian.marketing.usermicroservice.domain.spi.IRegisterUserPersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter.RegistrationUserAdapter;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper.IUserRegistrationRequestEntityMapper;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IUserRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RegisterUserBeanConfiguration {

  private final IUserRegistrationRequestEntityMapper mapper;
  private final IUserRegistrationRepository repository;

  @Bean
  public IRegisterUserPersistencePort registerUserPersistencePort() {
    return new RegistrationUserAdapter(repository, mapper);
  }

}
