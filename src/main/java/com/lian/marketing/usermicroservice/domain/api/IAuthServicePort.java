package com.lian.marketing.usermicroservice.domain.api;

import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;

public interface IAuthServicePort {
    String passwordEncoded(String password);
    void createUser(RegistrationUser user);
    String login(String email, String password);
}
