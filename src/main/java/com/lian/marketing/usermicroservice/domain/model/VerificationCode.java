package com.lian.marketing.usermicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationCode {
    private UUID userId;
    private String code;
    private LocalDateTime expiresAt;
    private Boolean isVerified;
    private String email;
}
