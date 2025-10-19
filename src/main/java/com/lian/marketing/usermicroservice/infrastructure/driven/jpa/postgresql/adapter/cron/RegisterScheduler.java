package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter.cron;

import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.HistoryRequestEntity;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserEntity;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserRegistrationRequestEntity;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IHistoryRequestRepository;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IUserRegistrationRepository;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RegisterScheduler {

  private final IUserRepository userRepository;
  private final IUserRegistrationRepository registrationRepository;
  private final IHistoryRequestRepository historyRequestRepository;

  @Scheduled(cron = "0 0/5 * * * ?")
  public void selectionRequests(){
    log.info("Starting selection request cron job");
    //Delete rejected requests
    List<UserRegistrationRequestEntity> requests = registrationRepository.findAll();
    requests.stream()
      .filter(r -> "REJECTED".equals(r.getStatus()))
      .forEach(reject -> moveRequestToHistory(reject, "Solicitud rechazada"));


    //Insert accepted requests
    requests.stream()
      .filter(r -> "ACCEPTED".equals(r.getStatus()))
      .forEach(request -> {
        userRepository.save(new UserEntity(
            null,
            request.getFirstName(),
            request.getLastName(),
            request.getEmail(),
            request.getPassword(),
            request.getBirthday(),
            "USER"
          ));
        moveRequestToHistory(request, "Solicitud aceptada");
      }
    );

    //Delete old pending requests - 1 day old
    requests.stream()
      .filter(r -> "PENDING".equals(r.getStatus()))
      .filter(r -> r.getCreatedAt().isBefore(java.time.LocalDate.now().minusDays(1)))
      .forEach(pending -> moveRequestToHistory(pending, "Solicitud pendiente eliminada por antiguedad"));

    log.info("Finish selection request cron job");
  }

  private void moveRequestToHistory(UserRegistrationRequestEntity request, String reason) {
    historyRequestRepository.save(new HistoryRequestEntity(
      null,
      request.getFirstName(),
      request.getLastName(),
      request.getEmail(),
      request.getStatus(),
      LocalDate.now(),
      reason
    ));
    registrationRepository.deleteById(request.getId());
  }

}
