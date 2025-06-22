package com.thomazllr.repository;

import com.thomazllr.domain.Anime;
import com.thomazllr.domain.Producer;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class ProducerHardCodedRepository {

    private final List<Producer> producers = new ArrayList<>(List.of(
            new Producer(v(), "Mappa", LocalDateTime.now()),
            new Producer(v(), "Ufotable",  LocalDateTime.now()),
            new Producer(v(), "MAD House",  LocalDateTime.now())
    ));

    public List<Producer> getAllProducers() {
        return producers;
    }


    public Producer findByName(String name) {
        return producers.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Producer findById(long id) {
        return producers.stream().filter(producer -> producer.getId() == id).findFirst().orElse(null);
    }

    public void delete(Producer producer) {
        producers.remove(producer);
    }

    public long v() {
        return ThreadLocalRandom.current().nextLong(10, 100);

    }

    public Producer add(Producer producer) {
        producers.add(producer);
        return producer;
    }
}
