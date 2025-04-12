package com.lian.marketing.usermicroservice.domain.mock;

import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.domain.model.VerificationCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class DomainMocks {
    private DomainMocks() {}

    public static User userMock(boolean isAdult) {
        LocalDate birthday = isAdult ? LocalDate.of(2004, 3, 18) : LocalDate.now();

        return new User(
                UUID.randomUUID(),
                "Erick",
                "Chaparro",
                "erick@gmail.com",
                "12345abc",
                birthday,
                false
        );
    }

    public static VerificationCode verificationCodeMock() {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUserId(UUID.randomUUID());
        verificationCode.setCode("123456");
        verificationCode.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        verificationCode.setIsVerified(false);
        verificationCode.setEmail("erick@gmail.com");
        return verificationCode;
    }

    public static VerificationCode expiredVerificationCodeMock() {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUserId(UUID.randomUUID());
        verificationCode.setCode("123456");
        verificationCode.setExpiresAt(LocalDateTime.now().minusMinutes(10));
        verificationCode.setIsVerified(false);
        verificationCode.setEmail("erick@gmail.com");
        return verificationCode;
    }
}
