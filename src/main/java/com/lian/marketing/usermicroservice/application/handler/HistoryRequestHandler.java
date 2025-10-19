package com.lian.marketing.usermicroservice.application.handler;

import com.lian.marketing.usermicroservice.domain.api.IHistoryRequestServicePort;
import com.lian.marketing.usermicroservice.domain.model.ContentPage;
import com.lian.marketing.usermicroservice.domain.model.HistoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryRequestHandler {
  private final IHistoryRequestServicePort historyRequestServicePort;

  public ContentPage<HistoryRequest> findAllHistoryRequests(int page, int size, String status){
    return historyRequestServicePort.findAllHistoryRequests(page, size, status);
  }
}
