package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AuthUseCase implements IAuthServicePort {

    private final PasswordEncoder passwordEncoder;


    @Override
    public String passwordEncoded(String password) {
        return passwordEncoder.encode(password);
    }
}
