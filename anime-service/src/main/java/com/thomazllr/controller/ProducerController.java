package com.thomazllr.controller;

import com.thomazllr.mapper.ProducerMapper;
import com.thomazllr.request.ProducerPostRequest;
import com.thomazllr.request.ProducerPostResponse;
import com.thomazllr.request.ProducerPutRequest;
import com.thomazllr.response.ProducerGetResponse;
import com.thomazllr.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private final ProducerService service;
    private final ProducerMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> findAll(@RequestParam(required = false) String name) {

        var producers = service.findAll(name);

        var response = mapper.toProducerGetResponseList(producers);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerGetResponse> findProducerById(@PathVariable long id) {
        var producer = service.findById(id);
        var response = mapper.toResponse(producer);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        var producer = mapper.toEntity(request);
        var producerSaved = service.save(producer);
        var response = mapper.toProducerPostResponse(producerSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.debug("Request to update producer : {}", request);
        var producer = mapper.toEntityFromPutRequest(request);
        service.update(producer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
