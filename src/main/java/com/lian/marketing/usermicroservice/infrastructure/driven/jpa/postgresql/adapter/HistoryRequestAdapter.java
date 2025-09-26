package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter;

import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.HistoryRequest;
import com.lian.marketing.usermicroservice.domain.spi.IHistoryRequestPersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.HistoryRequestEntity;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper.IHistoryRequestEntityMapper;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.repository.IHistoryRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class HistoryRequestAdapter implements IHistoryRequestPersistencePort {

  private final IHistoryRequestEntityMapper historyRequestEntityMapper;
  private final IHistoryRequestRepository historyRequestRepository;

  @Override
  public ContentPage<HistoryRequest> findAllHistoryRequests(int page, int size, String status) {
    Pageable pageable = PageRequest.of(page, size);
    Page<HistoryRequestEntity> requests = historyRequestRepository.findByStatus(pageable, status);
    List<HistoryRequest> historyList = historyRequestEntityMapper.toModelList(requests.getContent());
    return new ContentPage<>(
      requests.getTotalPages(),
      requests.getTotalElements(),
      requests.getPageable().getPageNumber(),
      requests.getPageable().getPageSize(),
      requests.isFirst(),
      requests.isLast(),
      historyList
    );
  }
}
