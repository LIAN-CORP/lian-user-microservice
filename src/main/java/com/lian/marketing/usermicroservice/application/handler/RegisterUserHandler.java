package com.lian.marketing.usermicroservice.application.handler;

import com.lian.marketing.usermicroservice.domain.api.IRegistrationServicePort;
import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterUserHandler {

  private final IRegistrationServicePort registrationServicePort;

  public void registerUser(UUID id, boolean approved){
    registrationServicePort.sendApproval(id, approved);
  }

  public ContentPage<RegistrationUser> findAllActiveRegistrationRequests(int page, int size){
    return registrationServicePort.findAllActiveRegistrationRequests(page, size);
  }

}
