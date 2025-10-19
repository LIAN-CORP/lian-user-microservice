package com.lian.marketing.usermicroservice.application.handler;

import com.lian.marketing.usermicroservice.application.dto.response.RegisterCodeResponse;
import com.lian.marketing.usermicroservice.application.mapper.IUserMapper;
import com.lian.marketing.usermicroservice.domain.api.IUserServicePort;
import com.lian.marketing.usermicroservice.domain.model.ExistsResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler {

    private final IUserMapper userMapper;
    private final IUserServicePort userServicePort;

    public ExistsResponse existsUserById(UUID id) {
        return userServicePort.userExistsById(id);
    }

    public RegisterCodeResponse generateRegisterCode(Jwt jwt){
        String code = userServicePort.generateRegisterCode(jwt);
        return new RegisterCodeResponse(code);
    }

}
