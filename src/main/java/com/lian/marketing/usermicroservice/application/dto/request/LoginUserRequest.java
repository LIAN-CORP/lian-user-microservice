package com.lian.marketing.usermicroservice.application.dto.request;

import com.lian.marketing.usermicroservice.application.constants.RequestConstants;
import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequest(
  @NotEmpty(message = RequestConstants.EMAIL_IS_NOT_VALID)
  String email,
  @NotEmpty(message = RequestConstants.PASSWORD_MUST_BE_NOT_NULL)
  String password
) {
}
