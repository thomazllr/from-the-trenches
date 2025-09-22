package com.thomazllr.producer;

import com.thomazllr.domain.Producer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProducerMapper {

    Producer toEntity(ProducerPostRequest request);

    ProducerGetResponse toResponse(Producer entity);

    ProducerPostResponse toProducerPostResponse(Producer entity);

    List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producerList);

    Producer toEntityFromPutRequest(ProducerPutRequest request);

}
