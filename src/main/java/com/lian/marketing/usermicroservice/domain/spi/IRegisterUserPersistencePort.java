package com.lian.marketing.usermicroservice.domain.spi;

import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;

import java.util.UUID;

public interface IRegisterUserPersistencePort {
  void saveRegisterUser(RegistrationUser registrationUser);
  boolean existsById(UUID id);
  void changeRegisterStatus(UUID id, String approved);
  ContentPage<RegistrationUser> findAllActiveRegistrationRequests(int page, int size);
}
