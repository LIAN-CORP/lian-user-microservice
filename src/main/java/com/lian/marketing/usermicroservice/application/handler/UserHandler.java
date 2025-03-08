package com.lian.marketing.usermicroservice.application.handler;

import com.lian.marketing.usermicroservice.application.dto.request.CreateUserRequest;
import com.lian.marketing.usermicroservice.application.mapper.IUserMapper;
import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandler {

    private final IUserMapper userMapper;
    private final IUserServicePort userServicePort;

    public void createUser(CreateUserRequest request) {
        userServicePort.createUser(userMapper.toModel(request));
    }

}
