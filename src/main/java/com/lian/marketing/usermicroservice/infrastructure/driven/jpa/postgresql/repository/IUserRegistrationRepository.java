package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository;

import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserRegistrationRequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IUserRegistrationRepository extends JpaRepository<UserRegistrationRequestEntity, UUID> {
  @Modifying
  @Query(value = "UPDATE user_registration_request SET status = :approved WHERE id = :id", nativeQuery = true)
  void updateRegisterStatus(UUID id, String approved);

  @Query(value = "SELECT * FROM user_registration_request ORDER BY created_at DESC", nativeQuery = true)
  Page<UserRegistrationRequestEntity> findAllActiveRegistrationRequests(Pageable pageable);

  @Modifying
  @Query(value = "DELETE FROM user_registration_request WHERE id IN (:ids)", nativeQuery = true)
  void deleteByIds(@Param("ids") List<UUID> ids);
}
