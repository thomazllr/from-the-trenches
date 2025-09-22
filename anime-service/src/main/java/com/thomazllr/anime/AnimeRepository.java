package com.thomazllr.anime;

import com.thomazllr.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    List<Anime> findAnimeByName(String name);
}
