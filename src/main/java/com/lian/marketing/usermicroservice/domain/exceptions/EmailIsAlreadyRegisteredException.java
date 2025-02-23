package com.lian.marketing.usermicroservice.domain.exceptions;

public class EmailIsAlreadyRegisteredException extends RuntimeException {
    public EmailIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
