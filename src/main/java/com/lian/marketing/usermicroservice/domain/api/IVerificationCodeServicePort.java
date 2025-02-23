package com.lian.marketing.usermicroservice.domain.api;

import java.util.UUID;

public interface IVerificationCodeServicePort {
    void createCode(UUID userId);
    void changeVerifiedStatusCode(UUID userId);
    void deleteCode(UUID userId);
}
