package com.lian.marketing.usermicroservice.domain.spi;

import com.lian.marketing.usermicroservice.domain.model.VerificationCode;

import java.util.Optional;
import java.util.UUID;

public interface IVerificationCodePersistencePort {
    void saveCode(VerificationCode code);
    Optional<VerificationCode> findVerificationCodeByUserId(UUID userId);
    void changeIsVerifiedStatus(VerificationCode code);
    void deleteVerificationCodeByUserId(UUID userId);
    Optional<VerificationCode> findByEmailAndCode(String email, String code);
}
