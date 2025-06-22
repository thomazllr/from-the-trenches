package com.thomazllr.mapper;

import com.thomazllr.domain.Producer;
import com.thomazllr.request.ProducerPostRequest;
import com.thomazllr.request.ProducerPutRequest;
import com.thomazllr.response.ProducerGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProducerMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(10, 100))")
    Producer toEntity(ProducerPostRequest request);

    ProducerGetResponse toResponse(Producer entity);

    Producer toEntityFromPutRequest(ProducerPutRequest request);

}
