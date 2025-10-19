package com.lian.marketing.usermicroservice.domain.exceptions;

public class BirthdayIsNullException extends RuntimeException {
    public BirthdayIsNullException(String message) {
        super(message);
    }
}
