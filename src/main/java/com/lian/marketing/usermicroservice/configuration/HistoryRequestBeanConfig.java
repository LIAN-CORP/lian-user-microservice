package com.lian.marketing.usermicroservice.configuration;

import com.lian.marketing.usermicroservice.domain.api.IHistoryRequestServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.HistoryRequestUseCase;
import com.lian.marketing.usermicroservice.domain.spi.IHistoryRequestPersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter.HistoryRequestAdapter;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper.IHistoryRequestEntityMapper;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IHistoryRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HistoryRequestBeanConfig {
  private final IHistoryRequestRepository repository;
  private final IHistoryRequestEntityMapper mapper;

  @Bean
  public IHistoryRequestPersistencePort historyRequestPersistencePort(){
    return new HistoryRequestAdapter(mapper, repository);
  }

  @Bean
  public IHistoryRequestServicePort historyRequestServicePort(){
    return new HistoryRequestUseCase(historyRequestPersistencePort());
  }
}
