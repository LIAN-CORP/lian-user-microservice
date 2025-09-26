package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "history_request")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class HistoryRequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "status")
  private String status;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column(name = "reason")
  private String reason;
}
