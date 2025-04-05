package com.lian.marketing.usermicroservice.application.constants;

public class RequestConstants {
    private RequestConstants() {}

    public static final String FIRST_NAME_MUST_BE_NOT_NULL = "First name must not be null";
    public static final String LAST_NAME_MUST_BE_NOT_NULL = "Last name must not be null";
    public static final String EMAIL_MUST_BE_NOT_NULL = "Email must not be null";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$";
    public static final String EMAIL_IS_NOT_VALID = "Email is not valid";
    public static final String PASSWORD_MUST_BE_NOT_NULL = "Password must not be null";
    public static final String BIRTHDAY_MUST_BE_NOT_NULL = "Birthday must not be null";
    public static final String VERIFICATION_CODE_MUST_BE_NOT_EMPTY = "Verification code must not be empty";
    public static final String VERIFICATION_CODE_IS_NOT_VALID = "Verification code is not valid";
}
