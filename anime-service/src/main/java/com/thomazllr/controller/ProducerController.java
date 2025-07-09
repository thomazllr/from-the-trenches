package com.thomazllr.controller;

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
        return ResponseEntity.ok(service.findAll(name).stream().map(mapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerGetResponse> findProducerById(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toResponse(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest request) {
        var producer = service.save(mapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(producer));

    }

    @PutMapping
    public ResponseEntity<ProducerGetResponse> update(@RequestBody ProducerPutRequest request) {

        log.debug("Request to update producer : {}", request);
        var producer = mapper.toEntityFromPutRequest(request);
        if (producer == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        service.update(producer);
        return ResponseEntity.ok(mapper.toResponse(producer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
