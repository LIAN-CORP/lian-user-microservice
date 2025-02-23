package com.lian.marketing.usermicroservice.domain.exceptions;

public class IsUnderAgeException extends RuntimeException {
    public IsUnderAgeException(String message) {
        super(message);
    }
}
