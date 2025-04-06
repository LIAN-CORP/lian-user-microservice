package com.lian.marketing.usermicroservice.application.dto.request;

import com.lian.marketing.usermicroservice.application.constants.RequestConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record VerifyUserRequest (
    @NotEmpty(message = RequestConstants.EMAIL_MUST_BE_NOT_NULL)
    @Email(message = RequestConstants.EMAIL_IS_NOT_VALID)
    String email,

    @NotEmpty(message = RequestConstants.VERIFICATION_CODE_MUST_BE_NOT_EMPTY)
    @Pattern(message = RequestConstants.VERIFICATION_CODE_IS_NOT_VALID, regexp = RequestConstants.CODE_REGEX)
    String code
) {}
