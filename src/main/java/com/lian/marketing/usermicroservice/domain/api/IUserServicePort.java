package com.lian.marketing.usermicroservice.domain.api;

import com.lian.marketing.usermicroservice.domain.model.ExistsResponse;
import com.lian.marketing.usermicroservice.domain.model.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface IUserServicePort {
    ExistsResponse userExistsById(UUID id);
    User findUserByEmail(String email);
    String generateRegisterCode(Jwt jwt);
    void validateUser(String email, String password, LocalDate birthday);
    UUID findAnyAdminUser();
    void createUser(User user);
    Optional<User> userExistsByEmail(String email);
}
