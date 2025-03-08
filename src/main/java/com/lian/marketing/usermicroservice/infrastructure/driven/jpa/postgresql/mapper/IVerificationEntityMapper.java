package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper;

import com.lian.marketing.usermicroservice.domain.model.VerificationCode;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.VerificationCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IVerificationEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    VerificationCodeEntity toEntity(VerificationCode verificationCode);
    VerificationCode toModel(VerificationCodeEntity codeEntity);
}
