package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper;

import com.lian.marketing.usermicroservice.domain.model.User;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserEntityMapper {
    UserEntity toEntity(User user);
    User toModel(UserEntity entity);
}
