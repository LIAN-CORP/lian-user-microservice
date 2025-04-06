package com.lian.marketing.usermicroservice.domain.api;

import java.util.UUID;

public interface IVerificationCodeServicePort {
    String createCode(UUID userId, String email);
    void changeVerifiedStatusCode(UUID userId);
    void deleteCode(UUID userId);
    void findByEmailAndCode(String email, String code);
}
