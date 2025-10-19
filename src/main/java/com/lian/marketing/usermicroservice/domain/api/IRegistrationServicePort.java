package com.lian.marketing.usermicroservice.domain.api;

import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;

import java.util.UUID;

public interface IRegistrationServicePort {
  void sendApproval(UUID id, boolean approved);
  ContentPage<RegistrationUser> findAllActiveRegistrationRequests(int page, int size);
}
