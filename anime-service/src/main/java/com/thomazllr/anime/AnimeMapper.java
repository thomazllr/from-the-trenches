package com.thomazllr.anime;

import com.thomazllr.domain.Anime;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnimeMapper {

    Anime toEntity(AnimePostRequest request);

    Anime toEntityFromAnimePutRequest(AnimePutRequest entity);

    List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> animeList);

    AnimeGetResponse toResponse(Anime entity);
}
