package com.lian.marketing.usermicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RegistrationUser {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private LocalDate birthday;
  private StatusRegistration status;
  private LocalDate createdAt;
  private UUID reviewedBy;
}
