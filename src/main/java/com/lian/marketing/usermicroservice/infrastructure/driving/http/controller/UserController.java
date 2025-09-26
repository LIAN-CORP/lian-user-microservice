package com.lian.marketing.usermicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.usermicroservice.application.dto.response.RegisterCodeResponse;
import com.lian.marketing.usermicroservice.application.handler.HistoryRequestHandler;
import com.lian.marketing.usermicroservice.application.handler.RegisterUserHandler;
import com.lian.marketing.usermicroservice.application.handler.UserHandler;
import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.ExistsResponse;
import com.lian.marketing.usermicroservice.domain.model.HistoryRequest;
import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserHandler userHandler;
    private final RegisterUserHandler registerUserHandler;
    private final HistoryRequestHandler historyRequestHandler;

    @GetMapping("/exists/{id}")
    public ResponseEntity<ExistsResponse> userExistsById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userHandler.existsUserById(id));
    }

    @PostMapping("/register/code")
    public ResponseEntity<RegisterCodeResponse> registerUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(userHandler.generateRegisterCode(jwt));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registration/accept/{id}")
    public ResponseEntity<Void> acceptRegistration(@PathVariable("id") UUID id) {
        registerUserHandler.registerUser(id, true);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registration/reject/{id}")
    public ResponseEntity<Void> rejectRegistration(@PathVariable("id") UUID id) {
        registerUserHandler.registerUser(id, false);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/registration/requests")
    public ResponseEntity<ContentPage<RegistrationUser>> getAllActiveRegistrationRequests(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(registerUserHandler.findAllActiveRegistrationRequests(page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/registration/history")
    public ResponseEntity<ContentPage<HistoryRequest>> getAllHistoryRegistrationRequests(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(value = "status", required = false) String status
    ) {
        return ResponseEntity.ok().body(historyRequestHandler.findAllHistoryRequests(page, size, status));
    }
}
