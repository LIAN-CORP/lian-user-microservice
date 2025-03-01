package com.lian.marketing.usermicroservice.domain;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.AuthUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthUseCaseTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    private IAuthServicePort authServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authServicePort = new AuthUseCase(passwordEncoder);
    }

    @Test
    void ShouldEncodePasswordSuccessfully() {
        String password = "password";
        String encodedPassword = "akjsdksajdk";
        when(authServicePort.passwordEncoded(password)).thenReturn(encodedPassword);

        String result = authServicePort.passwordEncoded(password);

        assertEquals(encodedPassword, result);
        verify(passwordEncoder, times(1)).encode(password);
    }
}
