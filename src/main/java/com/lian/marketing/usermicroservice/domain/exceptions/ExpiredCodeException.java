package com.lian.marketing.usermicroservice.domain.exceptions;

public class ExpiredCodeException extends RuntimeException {
    public ExpiredCodeException(String message) {
        super(message);
    }
}
