package com.lian.marketing.usermicroservice.domain.spi;

import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;

public interface IRegisterUserPersistencePort {
  void saveRegisterUser(RegistrationUser registrationUser);
}
