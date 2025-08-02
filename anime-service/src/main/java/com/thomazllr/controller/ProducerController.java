package com.thomazllr.controller;

import com.thomazllr.domain.Producer;
import com.thomazllr.mapper.ProducerMapper;
import com.thomazllr.request.ProducerPostRequest;
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
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest request) {
        var producer = mapper.toEntity(request);
        service.save(producer);
        var response = mapper.toResponse(producer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<ProducerGetResponse> update(@RequestBody ProducerPutRequest request) {
        log.debug("Request to update producer : {}", request);
        var producer = mapper.toEntityFromPutRequest(request);
        if (producer == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        service.update(producer);
        var response = mapper.toResponse(producer);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
