package com.thomazllr.data;

import com.thomazllr.domain.Producer;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Component
public class ProducersData {

    private final List<Producer> producers = new ArrayList<>(List.of(
            new Producer(v(), "Mappa", LocalDateTime.now()),
            new Producer(v(), "Ufotable", LocalDateTime.now()),
            new Producer(v(), "MAD House", LocalDateTime.now())
    ));

    public long v() {
        return ThreadLocalRandom.current().nextLong(10, 100);

    }

}
