package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IRegistrationServicePort;
import com.lian.marketing.usermicroservice.domain.constants.ExceptionConstants;
import com.lian.marketing.usermicroservice.domain.exceptions.RegistrationIdDoNotExists;
import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;
import com.lian.marketing.usermicroservice.domain.model.StatusRegistration;
import com.lian.marketing.usermicroservice.domain.spi.IRegisterUserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RegistrationUseCase implements IRegistrationServicePort {

  private final IRegisterUserPersistencePort registerUserPersistencePort;

  @Override
  public void sendApproval(UUID id, boolean approved) {
    if(registerUserPersistencePort.existsById(id)){
      throw new RegistrationIdDoNotExists(ExceptionConstants.REGISTRATION_RECORD_DO_NOT_EXIST);
    }
    StatusRegistration approval = approved ? StatusRegistration.ACCEPTED : StatusRegistration.REJECTED;
    registerUserPersistencePort.changeRegisterStatus(id, String.valueOf(approval));
  }

  @Override
  public ContentPage<RegistrationUser> findAllActiveRegistrationRequests(int page, int size) {
    return registerUserPersistencePort.findAllActiveRegistrationRequests(page, size);
  }

}
