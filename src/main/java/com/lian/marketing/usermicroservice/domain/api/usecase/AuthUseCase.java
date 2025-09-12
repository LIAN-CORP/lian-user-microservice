package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.ITokenServicePort;
import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.constants.ExceptionConstants;
import com.lian.marketing.usermicroservice.domain.exceptions.InvalidCredentialsException;
import com.lian.marketing.usermicroservice.domain.model.User;
import com.nimbusds.jose.JOSEException;
import de.mkammerer.argon2.Argon2;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class AuthUseCase implements IAuthServicePort {

    private final Argon2 argon2;
    private final IUserServicePort userServicePort;
    private final ITokenServicePort tokenServicePort;

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

    @Override
    public String login(String email, String password) {
        try {
            User user = userServicePort.findUserByEmail(email);
            if (!argon2.verify(user.getPassword(), password.getBytes(StandardCharsets.UTF_8))) {
                throw new InvalidCredentialsException(ExceptionConstants.INVALID_CREDENTIALS);
            }

            return tokenServicePort.generateToken(user.getId().toString());

        } catch (JOSEException e) {
            throw new RuntimeException("Error trying to create the token");
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
