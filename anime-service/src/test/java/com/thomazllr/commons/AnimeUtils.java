package com.thomazllr.commons;

import com.thomazllr.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {

    public List<Anime> createAnimes() {
        var hxh = Anime.builder().id(1L).name("hunter x hunter").build();
        var aot = Anime.builder().id(2L).name("attack on titan").build();
        var dandandan = Anime.builder().id(3L).name("dan dan dan").build();
        return new ArrayList<>(List.of(hxh, aot, dandandan));
    }

    public Anime createAnime() {
        return Anime.builder()
                .id(99L)
                .name("naruto shippuden")
                .build();
    }

}
