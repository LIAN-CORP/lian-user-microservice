package com.lian.marketing.usermicroservice.domain.exceptions;

public class RegistrationIdDoNotExists extends RuntimeException {
  public RegistrationIdDoNotExists(String message) {
    super(message);
  }
}
