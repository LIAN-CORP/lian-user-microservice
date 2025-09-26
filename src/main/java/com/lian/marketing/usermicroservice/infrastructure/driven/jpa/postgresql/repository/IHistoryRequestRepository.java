package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository;

import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.HistoryRequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IHistoryRequestRepository extends JpaRepository<HistoryRequestEntity, UUID> {
  @Query(value = "SELECT * FROM history_request WHERE status LIKE LOWER(CONCAT('%',:status, '%'))", nativeQuery = true)
  Page<HistoryRequestEntity> findByStatus(Pageable page, String status);
}
