package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.ITokenServicePort;
import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.constants.ExceptionConstants;
import com.lian.marketing.usermicroservice.domain.exceptions.InvalidCredentialsException;
import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;
import com.lian.marketing.usermicroservice.domain.model.StatusRegistration;
import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.domain.spi.IRegisterUserPersistencePort;
import com.nimbusds.jose.JOSEException;
import de.mkammerer.argon2.Argon2;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@RequiredArgsConstructor
public class AuthUseCase implements IAuthServicePort {

    private final Argon2 argon2;
    private final IUserServicePort userServicePort;
    private final ITokenServicePort tokenServicePort;
    private final IRegisterUserPersistencePort registerUserPersistencePort;

    @Override
    public String passwordEncoded(String password) {
        return argon2.hash(10, 65536, 2, password.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void createUser(RegistrationUser user) {
        user.setPassword(passwordEncoded(user.getPassword()));
        user.setStatus(StatusRegistration.PENDING);
        user.setCreatedAt(LocalDate.now());
        user.setReviewedBy(userServicePort.findAnyAdminUser());
        userServicePort.validateUser(user.getEmail(), user.getPassword(), user.getBirthday());
        registerUserPersistencePort.saveRegisterUser(user);
    }

    @Override
    public String login(String email, String password) {
        try {
            User user = userServicePort.findUserByEmail(email);
            if (!argon2.verify(user.getPassword(), password.getBytes(StandardCharsets.UTF_8))) {
                throw new InvalidCredentialsException(ExceptionConstants.INVALID_CREDENTIALS);
            }

            String token = tokenServicePort.generateToken(user.getId().toString(), user.getRole());
            return token + "|" + user.getRole();

        } catch (JOSEException e) {
            throw new RuntimeException("Error trying to create the token");
        } catch (InvalidCredentialsException e) {
            throw new InvalidCredentialsException(e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void createAdminUserSeeder(String firstName, String lastName, String email, String password, String birthdate) {
        if(userServicePort.userExistsByEmail(email).isPresent()){
            return;
        }
        User admin = new User();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setEmail(email);
        admin.setPassword(passwordEncoded(password));
        admin.setBirthday(LocalDate.parse(birthdate));
        admin.setRole("ADMIN");
        userServicePort.validateUser(admin.getEmail(), admin.getPassword(), admin.getBirthday());
        userServicePort.createUser(admin);
    }
}
