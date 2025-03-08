package com.lian.marketing.usermicroservice.domain;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.AuthUseCase;
import de.mkammerer.argon2.Argon2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthUseCaseTest {

    @Mock
    private Argon2 argon2;
    private IAuthServicePort authServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authServicePort = new AuthUseCase(argon2);
    }

    @Test
    void ShouldEncodePasswordSuccessfully() {
        String password = "password";
        String encodedPassword = "akjsdksajdk";
        when(authServicePort.passwordEncoded(password)).thenReturn(encodedPassword);

        String result = authServicePort.passwordEncoded(password);

        assertEquals(encodedPassword, result);
        verify(argon2, times(1)).hash(10, 65536, 2, password.getBytes(StandardCharsets.UTF_8));
    }
}
