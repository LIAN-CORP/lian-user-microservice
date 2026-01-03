package com.lian.marketing.usermicroservice.configuration;

import com.lian.marketing.usermicroservice.domain.api.IAuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdminSeeder implements CommandLineRunner {

  @Value("${app.default-admin.first-name}")
  private String firstName;
  @Value("${app.default-admin.last-name}")
  private String lastName;
  @Value("${app.default-admin.email}")
  private String email;
  @Value("${app.default-admin.password}")
  private String password;
  @Value("${app.default-admin.birthdate}")
  private String birthdate;

  private final IAuthServicePort authServicePort;

  @Override
  public void run(String... args) throws Exception {
    authServicePort.createAdminUserSeeder(firstName, lastName, email, password, birthdate);
  }
}
