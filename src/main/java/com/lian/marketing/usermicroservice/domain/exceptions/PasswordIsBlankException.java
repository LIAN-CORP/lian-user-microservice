package com.lian.marketing.usermicroservice.domain.exceptions;

public class PasswordIsBlankException extends RuntimeException {
  public PasswordIsBlankException(String message) {
    super(message);
  }
}
