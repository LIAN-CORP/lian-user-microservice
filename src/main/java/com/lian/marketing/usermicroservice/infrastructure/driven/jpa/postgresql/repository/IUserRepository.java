package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository;

import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);
}
