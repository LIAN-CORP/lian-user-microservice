package com.lian.marketing.usermicroservice.application.dto.request;

import com.lian.marketing.usermicroservice.application.constants.RequestConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record SendVerificationCodeRequest(
        @NotEmpty(message = RequestConstants.EMAIL_MUST_BE_NOT_NULL)
        @Email(message = RequestConstants.EMAIL_IS_NOT_VALID)
        String email
) {
}
