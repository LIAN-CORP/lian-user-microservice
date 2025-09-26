package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IHistoryRequestServicePort;
import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.HistoryRequest;
import com.lian.marketing.usermicroservice.domain.spi.IHistoryRequestPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HistoryRequestUseCase implements IHistoryRequestServicePort {

  private final IHistoryRequestPersistencePort historyRequestPersistencePort;

  @Override
  public ContentPage<HistoryRequest> findAllHistoryRequests(int page, int size, String status) {
    return historyRequestPersistencePort.findAllHistoryRequests(page, size, status);
  }
}
