package com.thomazllr.data;

import com.thomazllr.domain.Anime;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class AnimeData {

    private final List<Anime> animes = new ArrayList<>(List.of(
            new Anime(1L, "Naruto"),
            new Anime(2L, "DBZ"),
            new Anime(3L, "Hunter x Hunter")
    ));

    public List<Anime> getAnimes() {
        return animes;
    }
}
