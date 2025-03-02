package com.lian.marketing.usermicroservice.configuration;

import com.lian.marketing.usermicroservice.domain.api.IVerificationCodeServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.VerificationCodeUseCase;
import com.lian.marketing.usermicroservice.domain.spi.IVerificationCodePersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter.VerificationCodeAdapter;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper.IVerificationEntityMapper;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IVerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class VerificationBeanConfiguration {

    private final IVerificationCodeRepository verificationCodeRepository;
    private final IVerificationEntityMapper verificationEntityMapper;

    @Bean
    public IVerificationCodePersistencePort verificationCodePersistencePort() {
        return new VerificationCodeAdapter(verificationCodeRepository, verificationEntityMapper);
    }

    @Bean
    public IVerificationCodeServicePort verificationCodeServicePort() {
        return new VerificationCodeUseCase(verificationCodePersistencePort());
    }

}
