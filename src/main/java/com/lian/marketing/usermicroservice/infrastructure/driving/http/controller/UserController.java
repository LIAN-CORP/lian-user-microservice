package com.lian.marketing.usermicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.usermicroservice.application.dto.response.RegisterCodeResponse;
import com.lian.marketing.usermicroservice.application.handler.UserHandler;
import com.lian.marketing.usermicroservice.domain.model.ExistsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserHandler userHandler;

    @GetMapping("/exists/{id}")
    public ResponseEntity<ExistsResponse> userExistsById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userHandler.existsUserById(id));
    }

    @PostMapping("/register/code")
    public ResponseEntity<RegisterCodeResponse> registerUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(userHandler.generateRegisterCode(jwt));
    }
}
