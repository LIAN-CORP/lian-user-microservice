package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper;

import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserRegistrationRequestEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserRegistrationRequestEntityMapper {
  UserRegistrationRequestEntity toEntity(RegistrationUser user);
  RegistrationUser toModel(UserRegistrationRequestEntity entity);
  List<RegistrationUser> toModelList(List<UserRegistrationRequestEntity> entityList);
}
