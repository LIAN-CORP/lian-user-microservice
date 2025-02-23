package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.constants.ExceptionConstants;
import com.lian.marketing.usermicroservice.domain.exceptions.BirthdayIsNullException;
import com.lian.marketing.usermicroservice.domain.exceptions.EmailIsAlreadyRegisteredException;
import com.lian.marketing.usermicroservice.domain.exceptions.IsUnderAgeException;
import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IAuthServicePort authServicePort;

    @Override
    public void createUser(User user) {
        if(userPersistencePort.emailExists(user.getEmail())) {
            throw new EmailIsAlreadyRegisteredException(ExceptionConstants.EMAIL_IS_ALREADY_REGISTERED);
        }
        if(!isAdult(user.getBirthDate())){
            throw new IsUnderAgeException(ExceptionConstants.IS_UNDER_AGE);
        }
        user.setPassword(authServicePort.passwordEncoded(user.getPassword()));
        userPersistencePort.saveUser(user);
    }

    private boolean isAdult(LocalDate birthday){
        if(birthday == null){
            throw new BirthdayIsNullException(ExceptionConstants.BIRTHDAY_IS_NOT_VALID);
        }
        return Period.between(birthday, LocalDate.now()).getYears() >= 18;
    }
}
