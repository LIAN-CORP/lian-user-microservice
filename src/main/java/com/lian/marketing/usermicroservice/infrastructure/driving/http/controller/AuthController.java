package com.lian.marketing.usermicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.usermicroservice.application.dto.request.CreateUserRequest;
import com.lian.marketing.usermicroservice.application.dto.request.LoginUserRequest;
import com.lian.marketing.usermicroservice.application.dto.response.LoginUserResponse;
import com.lian.marketing.usermicroservice.application.handler.AuthHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthHandler authHandler;

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@Valid @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) CreateUserRequest request) {
        authHandler.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@Valid @RequestBody LoginUserRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authHandler.loginUser(request));
    }
}
