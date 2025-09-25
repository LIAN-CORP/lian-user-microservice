package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter.cron;

import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserEntity;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserRegistrationRequestEntity;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IUserRegistrationRepository;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
public class RegisterScheduler {

  private final IUserRepository userRepository;
  private final IUserRegistrationRepository registrationRepository;

  @Scheduled(cron = "0 0/15 * * * ?")
  public void selectionRequests(){
    //Delete rejected requests
    List<UserRegistrationRequestEntity> requests = registrationRepository.findAll();
    List<UUID> rejectedRequests = requests.stream()
      .filter(r -> "REJECTED".equals(r.getStatus()))
      .map(UserRegistrationRequestEntity::getId)
      .toList();
    if(!rejectedRequests.isEmpty()){
      registrationRepository.deleteByIds(rejectedRequests);
    }


    //Insert accepted requests
    requests.stream()
      .filter(r -> "ACCEPTED".equals(r.getStatus()))
      .forEach(request -> userRepository.save(new UserEntity(
        null,
        request.getFirstName(),
        request.getLastName(),
        request.getEmail(),
        request.getPassword(),
        request.getBirthday(),
        "USER"
      )));

    //Delete old pending requests - 1 day old
    requests.stream()
      .filter(r -> "PENDING".equals(r.getStatus()))
      .filter(r -> r.getCreatedAt().isBefore(java.time.LocalDate.now().minusDays(1)))
      .map(UserRegistrationRequestEntity::getId)
      .toList()
      .forEach(registrationRepository::deleteById);
  }

}
