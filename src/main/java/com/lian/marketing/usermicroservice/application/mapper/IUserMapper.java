package com.lian.marketing.usermicroservice.application.mapper;

import com.lian.marketing.usermicroservice.application.dto.request.CreateUserRequest;
import com.lian.marketing.usermicroservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isVerified", ignore = true)
    User toModel(CreateUserRequest request);
}
