package com.lian.marketing.usermicroservice.domain;

import com.lian.marketing.usermicroservice.domain.api.IVerificationCodeServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.VerificationCodeUseCase;
import com.lian.marketing.usermicroservice.domain.constants.ExceptionConstants;
import com.lian.marketing.usermicroservice.domain.exceptions.NoVerificationCodeIsAssociateWithUser;
import com.lian.marketing.usermicroservice.domain.model.VerificationCode;
import com.lian.marketing.usermicroservice.domain.spi.IVerificationCodePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class VerificationCodeUseCaseTest {

    @Mock
    private IVerificationCodePersistencePort verificationCodePersistencePort;

    private IVerificationCodeServicePort verificationCodeServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        verificationCodeServicePort = new VerificationCodeUseCase(verificationCodePersistencePort);
    }

    @Test
    void shouldUserCodeSuccessfully() {
        UUID uuid = UUID.randomUUID();

        verificationCodeServicePort.createCode(uuid);

        verify(verificationCodePersistencePort, times(1)).saveCode(argThat(code ->
            code.getUserId().equals(uuid) &&
            code.getExpiresAt().isAfter(LocalDateTime.now()) &&
            code.getExpiresAt().isBefore(LocalDateTime.now().plusMinutes(6)) &&
            !code.getIsVerified()
        ));
    }

    @Test
    void shouldChangeVerifiedStatusCodeSuccessfully() {
        UUID uuid = UUID.randomUUID();
        VerificationCode code = new VerificationCode(
                uuid,
                "456789",
                LocalDateTime.now().plusMinutes(5),
                false,
                "Email@gmail.com"
        );
        when(verificationCodePersistencePort.findVerificationCodeByUserId(uuid)).thenReturn(Optional.of(code));

        verificationCodeServicePort.changeVerifiedStatusCode(uuid);

        assertDoesNotThrow(() -> verificationCodeServicePort.createCode(uuid));
    }

    @Test
    void shouldThrowExceptionWhenAssociateCodeWithUserDoNotExist() {
        UUID uuid = UUID.randomUUID();
        when(verificationCodePersistencePort.findVerificationCodeByUserId(uuid)).thenReturn(Optional.empty());


        NoVerificationCodeIsAssociateWithUser ex = assertThrows(
                NoVerificationCodeIsAssociateWithUser.class,
                () -> verificationCodeServicePort.changeVerifiedStatusCode(uuid)
        );
        assertEquals(ExceptionConstants.NO_VERIFICATION_CODE_IS_ASSOCIATE_WITH_USER, ex.getMessage());
        verify(verificationCodePersistencePort, never()).changeIsVerifiedStatus(any());

    }

    @Test
    void shouldDeleteCodeSuccessfully() {
        UUID uuid = UUID.randomUUID();

        verificationCodeServicePort.deleteCode(uuid);

        verify(verificationCodePersistencePort, times(1)).deleteVerificationCodeByUserId(uuid);
    }

}
