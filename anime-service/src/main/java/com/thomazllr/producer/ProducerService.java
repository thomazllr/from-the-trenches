package com.thomazllr.producer;

import com.thomazllr.domain.Producer;
import com.thomazllr.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository repository;

    public Producer findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Producer Not Found"));
    }

    public List<Producer> findAll(String name) {
        return name == null ? repository.findAll() : repository.findProducerByName(name);
    }

    public void delete(Long id) {
        var producer = findById(id);
        repository.delete(producer);
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public void update(Producer producerToBeUpdate) {
        var producer = findById(producerToBeUpdate.getId());
        producerToBeUpdate.setCreatedAt(producer.getCreatedAt());
        repository.save(producerToBeUpdate);
    }


}

