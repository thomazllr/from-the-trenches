package com.thomazllr.service;

import com.thomazllr.domain.Producer;
import com.thomazllr.repository.ProducerHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerHardCodedRepository repository;

    public Producer findById(long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Producer> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public void delete(Producer producer) {
        repository.delete(producer);
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public void update(Producer producerToBeUpdate) {
        var producer = findById(producerToBeUpdate.getId());
        producerToBeUpdate.setCreatedAt(producer.getCreatedAt());
        repository.update(producerToBeUpdate);
    }


}

