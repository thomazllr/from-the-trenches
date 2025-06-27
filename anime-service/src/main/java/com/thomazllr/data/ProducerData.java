package com.thomazllr.data;

import com.thomazllr.domain.Producer;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class ProducerData {

    private final List<Producer> producers = new ArrayList<>(List.of(
            new Producer(1L, "Mappa", LocalDateTime.now()),
            new Producer(2L, "Ufotable", LocalDateTime.now()),
            new Producer(3L, "MAD House", LocalDateTime.now())
    ));

    public List<Producer> getProducers() {
        return producers;
    }
}
