package com.lian.marketing.usermicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class HistoryRequest {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String status;
  private String createdAt;
  private String reason;
}
