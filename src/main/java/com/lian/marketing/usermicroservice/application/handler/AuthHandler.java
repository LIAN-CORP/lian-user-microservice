package com.lian.marketing.usermicroservice.application.handler;

import com.lian.marketing.usermicroservice.application.dto.request.CreateUserRequest;
import com.lian.marketing.usermicroservice.application.dto.request.LoginUserRequest;
import com.lian.marketing.usermicroservice.application.dto.response.LoginUserResponse;
import com.lian.marketing.usermicroservice.application.mapper.IUserMapper;
import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHandler {

  private final IUserMapper userMapper;
  private final IAuthServicePort authServicePort;

  public void createUser(CreateUserRequest request){
    authServicePort.createUser(userMapper.toModel(request));
  }

  public LoginUserResponse loginUser(LoginUserRequest request){
    String accessToken = authServicePort.login(request.email(), request.password());
    String[] split = accessToken.split("\\|");
    return new LoginUserResponse(split[0], split[1]);
  }
}
