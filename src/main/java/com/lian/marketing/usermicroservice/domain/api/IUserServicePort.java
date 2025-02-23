package com.lian.marketing.usermicroservice.domain.api;

import com.lian.marketing.usermicroservice.domain.model.User;

public interface IUserServicePort {
    void createUser(User user);
}
