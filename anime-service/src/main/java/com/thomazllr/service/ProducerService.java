package com.thomazllr.service;

import com.thomazllr.domain.Producer;
import com.thomazllr.repository.ProducerHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerHardCodedRepository repository;

    public Producer findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Producer> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Producer> findAll() {
        return repository.findAll();
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

