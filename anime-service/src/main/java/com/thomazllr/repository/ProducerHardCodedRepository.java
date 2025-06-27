package com.thomazllr.repository;

import com.thomazllr.data.ProducerData;
import com.thomazllr.domain.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProducerHardCodedRepository {

    private final ProducerData producerData;

    public List<Producer> findAll() {
        return producerData.getProducers();
    }

    public List<Producer> findByName(String name) {
        return producerData.getProducers().stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    public Optional<Producer> findById(long id) {
        return producerData.getProducers().stream().filter(producer -> producer.getId() == id).findFirst();
    }

    public void delete(Producer producer) {
        producerData.getProducers().remove(producer);
    }

    public Producer save(Producer producer) {
        producerData.getProducers().add(producer);
        return producer;
    }

    public void update(Producer producer) {
        producerData.getProducers().remove(producer);
        producerData.getProducers().add(producer);
    }
}
