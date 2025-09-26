package com.lian.marketing.usermicroservice.domain.spi;

import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.HistoryRequest;

public interface IHistoryRequestPersistencePort {
  ContentPage<HistoryRequest> findAllHistoryRequests(int page, int size, String status);
}
