package com.thomazllr.mapper;

import com.thomazllr.domain.Anime;
import com.thomazllr.request.AnimePostRequest;
import com.thomazllr.request.AnimePutRequest;
import com.thomazllr.response.AnimeGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimeMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(10, 1000))")
    Anime toEntity(AnimePostRequest request);

    Anime toEntityFromAnimePutRequest(AnimePutRequest entity);

    AnimeGetResponse toResponse(Anime entity);
}
