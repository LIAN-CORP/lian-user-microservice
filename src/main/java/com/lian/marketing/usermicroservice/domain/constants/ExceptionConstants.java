package com.lian.marketing.usermicroservice.domain.constants;

public class ExceptionConstants {
    private ExceptionConstants() {}

    public static final String EMAIL_IS_ALREADY_REGISTERED = "Email is already registered.";
    public static final String BIRTHDAY_IS_NOT_VALID = "Birthday is not valid.";
    public static final String IS_UNDER_AGE = "Is under age.";
    public static final String NO_VERIFICATION_CODE_IS_ASSOCIATE_WITH_USER = "No verification code is associated with user";
    public static final String INVALID_VERIFICATION_CODE = "Invalid verification code.";
    public static final String EXPIRED_VERIFICATION_CODE = "Expired verification code.";
    public static final String USER_NOT_FOUND = "User not found with email: %s";
}
