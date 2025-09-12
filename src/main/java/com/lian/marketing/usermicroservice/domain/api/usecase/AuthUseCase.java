package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.constants.ExceptionConstants;
import com.lian.marketing.usermicroservice.domain.exceptions.EmailIsAlreadyRegisteredException;
import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.domain.spi.IAuthPersistencePort;
import de.mkammerer.argon2.Argon2;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class AuthUseCase implements IAuthServicePort {

    private final Argon2 argon2;
    private final IAuthPersistencePort authPersistencePort;
    private final IUserServicePort userServicePort;

    @Override
    public String passwordEncoded(String password) {
        return argon2.hash(10, 65536, 2, password.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void createUser(User user) {
        userServicePort.validateUser(user);
        user.setPassword(passwordEncoded(user.getPassword()));
        userServicePort.saveUser(user);
    }
}
