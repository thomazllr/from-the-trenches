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
        return repository.findById(id);
    }

    public Producer findByName(String name) {
        return repository.findByName(name);
    }

    public List<Producer> findAll() {
        return repository.getAllProducers();
    }

    public void delete(Producer anime) {
        repository.delete(anime);
    }

    public Producer save(Producer anime) {
        return repository.add(anime);
    }


}

