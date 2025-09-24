package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter;

import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.RegistrationUser;
import com.lian.marketing.usermicroservice.domain.spi.IRegisterUserPersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.UserRegistrationRequestEntity;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper.IUserRegistrationRequestEntityMapper;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IUserRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class RegistrationUserAdapter implements IRegisterUserPersistencePort {

  private final IUserRegistrationRepository repository;
  private final IUserRegistrationRequestEntityMapper mapper;

  @Override
  public void saveRegisterUser(RegistrationUser registrationUser) {
    repository.save(mapper.toEntity(registrationUser));
  }

  @Override
  public boolean existsById(UUID id) {
    return repository.findById(id).isEmpty();
  }

  @Override
  public void changeRegisterStatus(UUID id, String approved) {
    repository.updateRegisterStatus(id, approved);
  }

  @Override
  public ContentPage<RegistrationUser> findAllActiveRegistrationRequests(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<UserRegistrationRequestEntity> requests = repository.findAllActiveRegistrationRequests(pageable);
    List<RegistrationUser> registrationList = mapper.toModelList(requests.getContent());
    return new ContentPage<>(
      requests.getTotalPages(),
      requests.getTotalElements(),
      requests.getPageable().getPageNumber(),
      requests.getPageable().getPageSize(),
      requests.isFirst(),
      requests.isLast(),
      registrationList
    );
  }
}
