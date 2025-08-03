package com.thomazllr.controller;

import com.thomazllr.mapper.AnimeMapper;
import com.thomazllr.request.AnimePostRequest;
import com.thomazllr.request.AnimePutRequest;
import com.thomazllr.response.AnimeGetResponse;
import com.thomazllr.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@RequiredArgsConstructor
@Slf4j
public class AnimeController {

    private final AnimeService animeService;
    private final AnimeMapper mapper;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> findAll(@RequestParam(required = false) String name) {

        var animeList = animeService.findAll(name);

        var response = mapper.toAnimeGetResponseList(animeList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeGetResponse> findAnimeById(@PathVariable long id) {

        var anime = animeService.findByIdOrThrowNotFound(id);
        var response = mapper.toResponse(anime);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AnimeGetResponse> save(@RequestBody AnimePostRequest request) {
        var anime = mapper.toEntity(request);
        var animeSaved =animeService.save(anime);
        var response = mapper.toResponse(animeSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        var anime = animeService.findByIdOrThrowNotFound(id);

        if (anime == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        animeService.delete(anime.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {

        log.debug("Request to update anime : {}", request);

        var anime = mapper.toEntityFromAnimePutRequest(request);
        if (anime == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        animeService.update(anime);

        return ResponseEntity.noContent().build();
    }


}
