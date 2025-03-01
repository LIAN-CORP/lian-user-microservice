package com.lian.marketing.usermicroservice.domain;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.api.IVerificationCodeServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.UserUseCase;
import com.lian.marketing.usermicroservice.domain.exceptions.BirthdayIsNullException;
import com.lian.marketing.usermicroservice.domain.exceptions.EmailIsAlreadyRegisteredException;
import com.lian.marketing.usermicroservice.domain.exceptions.IsUnderAgeException;
import com.lian.marketing.usermicroservice.domain.mock.DomainMocks;
import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userUseCase = new UserUseCase(userPersistencePort, authServicePort, verificationCodeServicePort);
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
        user.setBirthDate(null);
        when(userPersistencePort.emailExists(user.getEmail())).thenReturn(false);

        userServicePort.changeVerifiedStatus(user);

        assertThrows(BirthdayIsNullException.class, () -> userUseCase.createUser(user));
        verify(userPersistencePort, times(1)).emailExists(user.getEmail());
        verify(authServicePort, times(0)).passwordEncoded(user.getPassword());
    }

}
