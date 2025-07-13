package com.lian.marketing.usermicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.usermicroservice.application.handler.UserHandler;
import com.lian.marketing.usermicroservice.domain.model.ExistsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
