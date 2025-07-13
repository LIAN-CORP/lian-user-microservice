package com.lian.marketing.usermicroservice.domain.api;

import com.lian.marketing.usermicroservice.domain.model.ExistsResponse;
import com.lian.marketing.usermicroservice.domain.model.User;

import java.util.UUID;

public interface IUserServicePort {
    void createUser(User user);
    void changeVerifiedStatus(User user);
    void verifyAccount(String email, String code);
    void sendCode(String email);
    ExistsResponse userExistsById(UUID id);
}
