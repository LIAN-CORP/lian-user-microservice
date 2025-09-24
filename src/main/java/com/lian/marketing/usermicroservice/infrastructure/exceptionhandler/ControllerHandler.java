package com.lian.marketing.usermicroservice.infrastructure.exceptionhandler;

import com.lian.marketing.usermicroservice.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                "METHOD_ARGUMENT_NOT_VALID_EXCEPTION"
        ));
    }

    @ExceptionHandler(EmailIsAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionResponse> handleEmailIsAlreadyRegistered(EmailIsAlreadyRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                "EMAIL_IS_ALREADY_REGISTERED_EXCEPTION"
        ));
    }

    @ExceptionHandler(IsUnderAgeException.class)
    public ResponseEntity<ExceptionResponse> handleIsUnderAgeException(IsUnderAgeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                "IS_UNDER_AGE_EXCEPTION"
        ));
    }

    @ExceptionHandler(SendEmailException.class)
    public ResponseEntity<ExceptionResponse> handleSendEmailException(SendEmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                "SEND_EMAIL_EXCEPTION"
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                "USER_NOT_FOUND_EXCEPTION"
        ));
    }

    @ExceptionHandler(BirthdayIsNullException.class)
    public ResponseEntity<ExceptionResponse> handleBirthdayIsNullException(BirthdayIsNullException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                "BIRTHDAY_IS_NULL_EXCEPTION"
        ));
    }

    @ExceptionHandler(NoVerificationCodeIsAssociateWithUser.class)
    public ResponseEntity<ExceptionResponse> handleNoVerificationCode(NoVerificationCodeIsAssociateWithUser ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                "NO_VERIFICATION_CODE_IS_ASSOCIATE_WITH_USER"
        ));
    }

    @ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCode(InvalidCodeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
           "INVALID_CODE_EXCEPTION"
        ));
    }

    @ExceptionHandler(ExpiredCodeException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredCode(ExpiredCodeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
           "EXPIRED_CODE_EXCEPTION"
        ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredCode(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
          HttpStatus.BAD_REQUEST.toString(),
          HttpStatus.BAD_REQUEST.value(),
          ex.getMessage(),
          LocalDateTime.now(),
          "INVALID_CREDENTIALS_EXCEPTION"
        ));
    }
}
