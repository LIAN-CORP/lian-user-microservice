package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository;

import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.VerificationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IVerificationCodeRepository extends JpaRepository<VerificationCodeEntity, Integer> {
    Optional<VerificationCodeEntity> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
    Optional<VerificationCodeEntity> findByEmailAndCode(String email, String code);
}
