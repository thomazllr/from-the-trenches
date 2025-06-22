package com.thomazllr.repository;

import com.thomazllr.data.ProducersData;
import com.thomazllr.domain.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProducerHardCodedRepository {

    @Autowired
    private ProducersData producersData;

    public List<Producer> findAll() {
        return producersData.getProducers();
    }

    public List<Producer> findByName(String name) {
        return producersData.getProducers().stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    public Optional<Producer> findById(long id) {
        return producersData.getProducers().stream().filter(producer -> producer.getId() == id).findFirst();
    }

    public void delete(Producer producer) {
        producersData.getProducers().remove(producer);
    }

    public Producer save(Producer producer) {
        producersData.getProducers().add(producer);
        return producer;
    }

    public void update(Producer producer) {
        producersData.getProducers().remove(producer);
        producersData.getProducers().add(producer);
    }
}
