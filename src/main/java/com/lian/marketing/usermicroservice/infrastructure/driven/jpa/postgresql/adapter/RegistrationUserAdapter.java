package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter;

import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;
import com.lian.marketing.usermicroservice.domain.spi.IRegisterUserPersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper.IUserRegistrationRequestEntityMapper;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IUserRegistrationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationUserAdapter implements IRegisterUserPersistencePort {

  private final IUserRegistrationRepository repository;
  private final IUserRegistrationRequestEntityMapper mapper;

  @Override
  public void saveRegisterUser(RegistrationUser registrationUser) {
    repository.save(mapper.toEntity(registrationUser));
  }
}
