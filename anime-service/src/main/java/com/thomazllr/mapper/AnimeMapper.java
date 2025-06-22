package com.thomazllr.mapper;

import com.thomazllr.domain.Anime;
import com.thomazllr.domain.Producer;
import com.thomazllr.request.AnimePostRequest;
import com.thomazllr.request.ProducerPostRequest;
import com.thomazllr.response.AnimeGetResponse;
import com.thomazllr.response.ProducerGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimeMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(10, 1000))")
    Anime toEntity(AnimePostRequest request);

    AnimeGetResponse toResponse(Anime entity);
}
