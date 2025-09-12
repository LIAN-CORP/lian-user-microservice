package com.lian.marketing.usermicroservice.domain.api;

import com.lian.marketing.usermicroservice.domain.model.ExistsResponse;
import com.lian.marketing.usermicroservice.domain.model.User;

import java.util.UUID;

public interface IUserServicePort {
    ExistsResponse userExistsById(UUID id);
    void validateUser(User user);
    void saveUser(User user);
}
