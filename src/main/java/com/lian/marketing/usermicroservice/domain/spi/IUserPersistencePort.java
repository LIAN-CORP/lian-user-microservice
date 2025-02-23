package com.lian.marketing.usermicroservice.domain.spi;

import com.lian.marketing.usermicroservice.domain.model.User;

public interface IUserPersistencePort {
    void saveUser(User user);
    boolean emailExists(String email);
}
