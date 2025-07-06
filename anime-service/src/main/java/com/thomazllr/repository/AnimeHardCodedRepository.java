package com.thomazllr.repository;

import com.thomazllr.data.AnimeData;
import com.thomazllr.domain.Anime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnimeHardCodedRepository {

    private final AnimeData animeData;

    public List<Anime> findAll() {
        return animeData.getAnimes();
    }

    public List<Anime> findByName(String name) {
        return animeData.getAnimes().stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    public Optional<Anime> findById(long id) {
        return animeData.getAnimes().stream().filter(anime -> anime.getId() == id).findFirst();
    }

    public void delete(Anime anime) {
        animeData.getAnimes().remove(anime);
    }

    public Anime save(Anime anime) {
        animeData.getAnimes().add(anime);
        return anime;
    }

    public void update(Anime anime) {
        animeData.getAnimes().remove(anime);
        animeData.getAnimes().add(anime);
    }

}
