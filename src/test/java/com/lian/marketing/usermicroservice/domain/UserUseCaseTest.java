package com.lian.marketing.usermicroservice.domain;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.IMailSenderServicePort;
import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.api.IVerificationCodeServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.UserUseCase;
import com.lian.marketing.usermicroservice.domain.exceptions.*;
import com.lian.marketing.usermicroservice.domain.mock.DomainMocks;
import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.domain.spi.IUserPersistencePort;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IAuthServicePort authServicePort;
    @Mock
    private IVerificationCodeServicePort verificationCodeServicePort;
    @Mock
    private IMailSenderServicePort mailSenderServicePort;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userUseCase = new UserUseCase(userPersistencePort, authServicePort, verificationCodeServicePort, mailSenderServicePort);
    }

    @Test
    void ShouldCreateUserSuccessfully() {
        //Arrange
        User user = DomainMocks.userMock(true);
        when(userPersistencePort.emailExists(user.getEmail())).thenReturn(false);
        when(authServicePort.passwordEncoded(user.getPassword())).thenReturn(user.getPassword());


        //Act
        userServicePort.createUser(user);

        //Assert
        assertDoesNotThrow(() -> userUseCase.createUser(user));
        verify(userPersistencePort, times(1)).emailExists(user.getEmail());
        verify(authServicePort, times(1)).passwordEncoded(user.getPassword());
        verify(userPersistencePort, times(1)).saveUser(user);
    }

    @Test
    void ShouldThrowEmailIsAlreadyRegisteredException() {
        //Arrange
        User user = DomainMocks.userMock(true);
        when(userPersistencePort.emailExists(user.getEmail())).thenReturn(true);

        //Act
        userServicePort.createUser(user);

        //Assert
        assertThrows(EmailIsAlreadyRegisteredException.class, () -> userUseCase.createUser(user));
        verify(userPersistencePort, times(1)).emailExists(user.getEmail());
        verify(authServicePort, times(0)).passwordEncoded(user.getPassword());
        verify(userPersistencePort, times(0)).saveUser(user);

    }

    @Test
    void ShouldThrowIsUnderAgeException() {
        //Arrange
        User user = DomainMocks.userMock(false);
        when(userPersistencePort.emailExists(user.getEmail())).thenReturn(false);

        //Act
        userServicePort.createUser(user);

        //Assert
        assertThrows(IsUnderAgeException.class, () -> userUseCase.createUser(user));
        verify(userPersistencePort, times(1)).emailExists(user.getEmail());
        verify(authServicePort, times(0)).passwordEncoded(user.getPassword());
        verify(userPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void ShouldChangeVerifiedStatusSuccessfully() {
        User user = DomainMocks.userMock(true);
        when(userPersistencePort.emailExists(user.getEmail())).thenReturn(true);

        userUseCase.changeVerifiedStatus(user);

        verify(userPersistencePort, times(1)).emailExists(user.getEmail());
        verify(userPersistencePort, times(1)).saveUser(user);
        verify(verificationCodeServicePort, times(1)).deleteCode(user.getId());
    }

    @Test
    void ShouldThrowExceptionToChangeVerifiedStatusWhenEmailDoesNotExist() {
        User user = DomainMocks.userMock(true);
        when(userPersistencePort.emailExists(user.getEmail())).thenReturn(false);

        userServicePort.changeVerifiedStatus(user);

        assertThrows(EmailIsAlreadyRegisteredException.class, () -> userUseCase.changeVerifiedStatus(user));
        verify(userPersistencePort, times(1)).emailExists(user.getEmail());
        verify(authServicePort, times(0)).passwordEncoded(user.getPassword());
        verify(userPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void ShouldThrowExceptionWhenBirthdayIsNull() {
        User user = DomainMocks.userMock(true);
        user.setBirthday(null);
        when(userPersistencePort.emailExists(user.getEmail())).thenReturn(false);

        userServicePort.changeVerifiedStatus(user);

        assertThrows(BirthdayIsNullException.class, () -> userUseCase.createUser(user));
        verify(userPersistencePort, times(1)).emailExists(user.getEmail());
        verify(authServicePort, times(0)).passwordEncoded(user.getPassword());
    }

    @Test
    void shouldThrowsSendEmailExceptionWhenEmailSenderFails() throws MessagingException {
        User user = DomainMocks.userMock(true);
        UUID userId = UUID.randomUUID();
        String code = "112233";
        when(userPersistencePort.emailExists(user.getEmail())).thenReturn(false);
        when(authServicePort.passwordEncoded(user.getPassword())).thenReturn("hashed123");
        when(userPersistencePort.saveUser(user)).thenReturn(userId);
        when(verificationCodeServicePort.createCode(userId, user.getEmail())).thenReturn(code);
        doThrow(new MessagingException("Error")).when(mailSenderServicePort).sendVerificationEmail(user.getEmail(), code);

        SendEmailException exception = assertThrows(SendEmailException.class, () -> userUseCase.createUser(user));
        assertEquals("Error", exception.getMessage());
    }

    @Test
    void shouldVerifyAccountSuccessfully() {
        //Arrange
        User user = DomainMocks.userMock(true);
        String code = "112233";
        doNothing().when(verificationCodeServicePort).findByEmailAndCode(user.getEmail(), code);
        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        //Act
        userUseCase.verifyAccount(user.getEmail(), code);

        //Assert
        verify(verificationCodeServicePort, times(1)).findByEmailAndCode(user.getEmail(), code);
        verify(userPersistencePort, times(1)).findByEmail(user.getEmail());
        verify(userPersistencePort, times(1)).saveUser(user);
        verify(verificationCodeServicePort, times(1)).deleteCode(user.getId());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserNotExists() {
        //Arrange
        User user = DomainMocks.userMock(true);
        String code = "112233";
        doNothing().when(verificationCodeServicePort).findByEmailAndCode(user.getEmail(), code);
        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(UserNotFoundException.class, () -> userUseCase.verifyAccount(user.getEmail(), code));
        verify(verificationCodeServicePort, times(1)).findByEmailAndCode(user.getEmail(), code);
        verify(userPersistencePort, times(1)).findByEmail(user.getEmail());
        verify(userPersistencePort, never()).saveUser(user);
        verify(verificationCodeServicePort, never()).deleteCode(user.getId());
    }

    @Test
    void shouldSendCodeSuccessfully() {
        //Arrange
        User user = DomainMocks.userMock(true);
        String code = "112233";
        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(verificationCodeServicePort.createCode(user.getId(), user.getEmail())).thenReturn(code);

        //Act
        userUseCase.sendCode(user.getEmail());

        //Assert
        verify(userPersistencePort, times(1)).findByEmail(user.getEmail());
        verify(verificationCodeServicePort, times(1)).createCode(user.getId(), user.getEmail());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserNotExistsToSendCode() {
        //Arrange
        User user = DomainMocks.userMock(true);
        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(UserNotFoundException.class, () -> userUseCase.sendCode(user.getEmail()));
        verify(userPersistencePort, times(1)).findByEmail(user.getEmail());
        verify(verificationCodeServicePort, never()).createCode(user.getId(), user.getEmail());
    }

    @Test
    void shouldThrowSendEmailExceptionWhenEmailSenderFails() throws MessagingException {
        //Arrange
        User user = DomainMocks.userMock(true);
        String code = "112233";
        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(verificationCodeServicePort.createCode(user.getId(), user.getEmail())).thenReturn(code);
        doThrow(new MessagingException("Error")).when(mailSenderServicePort).sendVerificationEmail(user.getEmail(), code);

        //Act & Assert
        SendEmailException ex = assertThrows(SendEmailException.class, () -> userUseCase.sendCode(user.getEmail()));
        assertEquals("Error", ex.getMessage());
        verify(userPersistencePort, times(1)).findByEmail(user.getEmail());
        verify(verificationCodeServicePort, times(1)).createCode(user.getId(), user.getEmail());
    }

}
