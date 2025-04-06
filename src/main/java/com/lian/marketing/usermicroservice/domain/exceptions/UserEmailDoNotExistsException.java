package com.lian.marketing.usermicroservice.domain.exceptions;

public class UserEmailDoNotExistsException extends RuntimeException {
    public UserEmailDoNotExistsException(String message) {
        super(message);
    }
}
