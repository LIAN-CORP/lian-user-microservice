package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.IMailSenderServicePort;
import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.api.IVerificationCodeServicePort;
import com.lian.marketing.usermicroservice.domain.constants.ExceptionConstants;
import com.lian.marketing.usermicroservice.domain.exceptions.BirthdayIsNullException;
import com.lian.marketing.usermicroservice.domain.exceptions.EmailIsAlreadyRegisteredException;
import com.lian.marketing.usermicroservice.domain.exceptions.IsUnderAgeException;
import com.lian.marketing.usermicroservice.domain.exceptions.SendEmailException;
import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.domain.spi.IUserPersistencePort;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IAuthServicePort authServicePort;
    private final IVerificationCodeServicePort verificationCodeServicePort;
    private final IMailSenderServicePort mailSenderServicePort;

    @Override
    public void createUser(User user) {
        if(userPersistencePort.emailExists(user.getEmail())) {
            throw new EmailIsAlreadyRegisteredException(ExceptionConstants.EMAIL_IS_ALREADY_REGISTERED);
        }
        if(!isAdult(user.getBirthday())){
            throw new IsUnderAgeException(ExceptionConstants.IS_UNDER_AGE);
        }
        user.setPassword(authServicePort.passwordEncoded(user.getPassword()));
        UUID userId = userPersistencePort.saveUser(user);

        // Logic to call dependencies to get and send verification code
        String code = verificationCodeServicePort.createCode(userId);
        try {
            mailSenderServicePort.sendVerificationEmail(user.getEmail(), code);
        } catch (MessagingException ex) {
            throw new SendEmailException(ex.getMessage());
        }
    }

    @Override
    public void changeVerifiedStatus(User user) {
        if(!userPersistencePort.emailExists(user.getEmail())) {
            throw new EmailIsAlreadyRegisteredException(ExceptionConstants.EMAIL_IS_ALREADY_REGISTERED);
        }
        user.setIsVerified(true);
        userPersistencePort.saveUser(user); //Se debe guardar primero el status en la base de datos
        verificationCodeServicePort.deleteCode(user.getId());
    }

    private boolean isAdult(LocalDate birthday){
        if(birthday == null){
            throw new BirthdayIsNullException(ExceptionConstants.BIRTHDAY_IS_NOT_VALID);
        }
        return Period.between(birthday, LocalDate.now()).getYears() >= 18;
    }
}
