package com.thomazllr.service;

import com.thomazllr.domain.Anime;
import com.thomazllr.repository.AnimeHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeHardCodedRepository repository;

    public Anime findById(long id) {
        return repository.findById(id);
    }

    public Anime findByName(String name) {
        return repository.findByName(name);
    }

    public List<Anime> findAll() {
        return repository.findAll();
    }

    public void delete(Anime anime) {
        repository.delete(anime);
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }
}

