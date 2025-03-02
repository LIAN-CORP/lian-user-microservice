package com.lian.marketing.usermicroservice.domain.spi;

import com.lian.marketing.usermicroservice.domain.model.User;

import java.util.UUID;

public interface IUserPersistencePort {
    UUID saveUser(User user);
    boolean emailExists(String email);
}
