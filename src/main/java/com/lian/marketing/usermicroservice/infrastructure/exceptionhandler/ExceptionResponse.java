package com.lian.marketing.usermicroservice.infrastructure.exceptionhandler;

import java.time.LocalDateTime;

public record ExceptionResponse (
        String status,
        int code,
        String message,
        LocalDateTime timestamp
) { }
