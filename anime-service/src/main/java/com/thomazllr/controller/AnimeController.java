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
        return ResponseEntity.ok(animeService.findAll(name).stream().map(mapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeGetResponse> findAnimeById(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toResponse(animeService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AnimeGetResponse> save(@RequestBody AnimePostRequest request) {
        var anime = animeService.save(mapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(anime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        var anime = animeService.findById(id);

        if (anime == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        animeService.delete(anime.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<AnimeGetResponse> update(@RequestBody AnimePutRequest request) {

        log.debug("Request to update anime : {}", request);

        var anime = mapper.toEntityFromAnimePutRequest(request);
        if (anime == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        animeService.update(anime);

        return ResponseEntity.ok(mapper.toResponse(anime));
    }


}
