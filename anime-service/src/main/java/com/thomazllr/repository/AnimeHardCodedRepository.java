package com.thomazllr.repository;

import com.thomazllr.domain.Anime;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class AnimeHardCodedRepository {


    private final List<Anime> animeArrayList = new ArrayList<>(List.of(
            new Anime(generateId(), "Naruto"),
            new Anime(generateId(), "Dragon Ball"),
            new Anime(generateId(), "Hunter x Hunter")
    ));

    public List<Anime> getAllAnimes() {
        return animeArrayList;
    }


    public Anime findByName(String name) {
        return animeArrayList.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Anime findById(long id) {
        return animeArrayList.stream().filter(anime -> anime.getId() == id).findFirst().orElse(null);
    }

    public void delete(Anime anime) {
        animeArrayList.remove(anime);
    }

    public Anime add(Anime anime) {
        animeArrayList.add(anime);
        return anime;
    }

    public long generateId() {
        return ThreadLocalRandom.current().nextLong(10, 1000);
    }

}
