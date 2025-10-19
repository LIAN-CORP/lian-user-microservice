package com.lian.marketing.usermicroservice.domain.api;

import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.HistoryRequest;

public interface IHistoryRequestServicePort {
  ContentPage<HistoryRequest> findAllHistoryRequests(int page, int size, String status);
}
