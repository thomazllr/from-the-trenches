package com.thomazllr.anime;

import com.thomazllr.domain.Anime;
import com.thomazllr.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository repository;

    public List<Anime> findAll(String name) {
        return name == null ? repository.findAll() : repository.findAnimeByName(name);
    }

    public Anime findByIdOrThrowNotFound(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anime Not Found"));
    }

    public void delete(Long id) {
        var anime = this.findByIdOrThrowNotFound(id);
        repository.delete(anime);
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }

    public void update(Anime animeToUpdate) {
        assertAnimeExists(animeToUpdate.getId());

        repository.save(animeToUpdate);
    }

    public void assertAnimeExists(Long id) {
        findByIdOrThrowNotFound(id);
    }

}

