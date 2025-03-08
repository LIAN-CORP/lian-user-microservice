package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter;

import com.lian.marketing.usermicroservice.domain.model.VerificationCode;
import com.lian.marketing.usermicroservice.domain.spi.IVerificationCodePersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.VerificationCodeEntity;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper.IVerificationEntityMapper;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IVerificationCodeRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class VerificationCodeAdapter implements IVerificationCodePersistencePort {

    private final IVerificationCodeRepository verificationRepository;
    private final IVerificationEntityMapper verificationMapper;

    @Override
    public void saveCode(VerificationCode code) {
        verificationRepository.save(verificationMapper.toEntity(code));
    }

    @Override
    public Optional<VerificationCode> findVerificationCodeByUserId(UUID userId) {
        Optional<VerificationCodeEntity> entity = verificationRepository.findByUserId(userId);
        return entity.map(verificationMapper::toModel);
    }

    @Override
    public void changeIsVerifiedStatus(VerificationCode code) {
        verificationRepository.save(verificationMapper.toEntity(code));
    }

    @Override
    public void deleteVerificationCodeByUserId(UUID userId) {
        verificationRepository.deleteByUserId(userId);
    }

    @Override
    public Optional<VerificationCode> findByEmailAndCode(String email, String code) {
        Optional<VerificationCodeEntity> entity = verificationRepository.findByEmailAndCode(email, code);
        return entity.map(verificationMapper::toModel);
    }
}
