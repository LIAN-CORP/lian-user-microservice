package com.lian.marketing.usermicroservice.domain.exceptions;

public class NoVerificationCodeIsAssociateWithUser extends RuntimeException {
    public NoVerificationCodeIsAssociateWithUser(String message) {
        super(message);
    }
}
