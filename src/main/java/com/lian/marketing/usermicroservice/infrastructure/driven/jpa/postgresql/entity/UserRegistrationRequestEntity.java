package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_registration_request")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class UserRegistrationRequestEntity {
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

  @Column(name = "password")
  private String password;

  @Column(name = "birthday")
  private LocalDate birthday;

  @Column(name = "status")
  private String status;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column(name = "reviewed_by")
  private UUID reviewedBy;

}
