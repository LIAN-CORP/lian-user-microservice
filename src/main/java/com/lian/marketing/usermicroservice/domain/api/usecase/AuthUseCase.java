package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import de.mkammerer.argon2.Argon2;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class AuthUseCase implements IAuthServicePort {

    private final Argon2 argon2;


    @Override
    public String passwordEncoded(String password) {
        return argon2.hash(10, 65536, 2, password.getBytes(StandardCharsets.UTF_8));
    }
}
