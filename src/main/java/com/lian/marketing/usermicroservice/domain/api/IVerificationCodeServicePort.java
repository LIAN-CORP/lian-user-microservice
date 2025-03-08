package com.lian.marketing.usermicroservice.domain.api;

import java.util.UUID;

public interface IVerificationCodeServicePort {
    String createCode(UUID userId);
    void changeVerifiedStatusCode(UUID userId);
    void deleteCode(UUID userId);
}
