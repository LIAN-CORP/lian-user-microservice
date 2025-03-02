package com.lian.marketing.usermicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.usermicroservice.application.dto.request.CreateUserRequest;
import com.lian.marketing.usermicroservice.application.handler.UserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserHandler userHandler;

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest request) {
        userHandler.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
