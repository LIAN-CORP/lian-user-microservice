package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.mapper;

import com.lian.marketing.usermicroservice.domain.model.HistoryRequest;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.entity.HistoryRequestEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IHistoryRequestEntityMapper {
  HistoryRequest toModel(HistoryRequestEntity entity);
  List<HistoryRequest> toModelList(List<HistoryRequestEntity> entityList);
}
