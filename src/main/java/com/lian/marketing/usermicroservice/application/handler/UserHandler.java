package com.lian.marketing.usermicroservice.application.handler;

import com.lian.marketing.usermicroservice.application.dto.request.CreateUserRequest;
import com.lian.marketing.usermicroservice.application.dto.request.SendVerificationCodeRequest;
import com.lian.marketing.usermicroservice.application.dto.request.VerifyUserRequest;
import com.lian.marketing.usermicroservice.application.mapper.IUserMapper;
import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.model.ExistsResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler {

    private final IUserMapper userMapper;
    private final IUserServicePort userServicePort;

    public void createUser(CreateUserRequest request) {
        userServicePort.createUser(userMapper.toModel(request));
    }

    public void verifyAccount(VerifyUserRequest request) {
        userServicePort.verifyAccount(request.email(), request.code());
    }

    public void sendCode(SendVerificationCodeRequest request) {
        userServicePort.sendCode(request.email());
    }

    public ExistsResponse existsUserById(UUID id) {
        return userServicePort.userExistsById(id);
    }

}
