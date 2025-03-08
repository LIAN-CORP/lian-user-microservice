package com.lian.marketing.usermicroservice.application.dto.request;

import com.lian.marketing.usermicroservice.application.constants.RequestConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateUserRequest (
        @NotEmpty(message = RequestConstants.FIRST_NAME_MUST_BE_NOT_NULL)
        String firstName,

        @NotEmpty(message = RequestConstants.LAST_NAME_MUST_BE_NOT_NULL)
        String lastName,

        @NotEmpty(message = RequestConstants.EMAIL_MUST_BE_NOT_NULL)
        @Email(message = RequestConstants.EMAIL_IS_NOT_VALID)
        String email,

        @NotEmpty(message = RequestConstants.PASSWORD_MUST_BE_NOT_NULL)
        String password,

        @NotNull(message = RequestConstants.BIRTHDAY_MUST_BE_NOT_NULL)
        LocalDate birthday
) {
}
