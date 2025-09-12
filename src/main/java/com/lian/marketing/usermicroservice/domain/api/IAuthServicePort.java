package com.lian.marketing.usermicroservice.domain.api;

import com.lian.marketing.usermicroservice.domain.model.User;

public interface IAuthServicePort {
    String passwordEncoded(String password);
    void createUser(User user);
}
