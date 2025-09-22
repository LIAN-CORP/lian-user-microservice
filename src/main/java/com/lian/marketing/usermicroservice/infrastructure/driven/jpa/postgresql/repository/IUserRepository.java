package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository;

import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT ID FROM _USER WHERE ROLE = 'ADMIN' LIMIT 1", nativeQuery = true)
    UUID findAnyAdminUser();
}
