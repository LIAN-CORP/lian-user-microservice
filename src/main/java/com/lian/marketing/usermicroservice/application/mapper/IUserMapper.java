package com.lian.marketing.usermicroservice.application.mapper;

import com.lian.marketing.usermicroservice.application.dto.request.CreateUserRequest;
import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "reviewedBy", ignore = true)
    RegistrationUser toModel(CreateUserRequest request);
}
