package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.constants.ExceptionConstants;
import com.lian.marketing.usermicroservice.domain.exceptions.*;
import com.lian.marketing.usermicroservice.domain.model.ExistsResponse;
import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    @Override
    public ExistsResponse userExistsById(UUID id) {
        boolean exists = userPersistencePort.userExistsById(id);
        return new ExistsResponse(exists);
    }

    @Override
    public void validateUser(String email, String password, LocalDate birthday) {
        if(password == null || password.isBlank()) {
            throw new PasswordIsBlankException(ExceptionConstants.PASSWORD_IS_BLANK);
        }
        if(userPersistencePort.emailExists(email)){
            throw new EmailIsAlreadyRegisteredException(ExceptionConstants.EMAIL_IS_ALREADY_REGISTERED);
        }

        if(!isAdult(birthday)){
            throw new IsUnderAgeException(ExceptionConstants.IS_UNDER_AGE);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userPersistencePort.findByEmail(email)
          .orElseThrow(() -> new InvalidCredentialsException(ExceptionConstants.INVALID_CREDENTIALS));
    }



    @Override
    public String generateRegisterCode(Jwt jwt) {
        String id = jwt.getSubject();
        Optional<User> user = userPersistencePort.findUserById(UUID.fromString(id));
        if(user.isEmpty()){
            throw new InvalidCredentialsException(ExceptionConstants.INVALID_CREDENTIALS);
        }

        return "";
    }

    @Override
    public UUID findAnyAdminUser() {
        return userPersistencePort.findAnyAdminUser();
    }

    @Override
    public void createUser(User user) {
        userPersistencePort.saveUser(user);
    }

    @Override
    public Optional<User> userExistsByEmail(String email) {
        return userPersistencePort.findByEmail(email);
    }

    private boolean isAdult(LocalDate birthday){
        if(birthday == null){
            throw new BirthdayIsNullException(ExceptionConstants.BIRTHDAY_IS_NOT_VALID);
        }
        return Period.between(birthday, LocalDate.now()).getYears() >= 18;
    }

}
