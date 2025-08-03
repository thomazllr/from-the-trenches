package com.thomazllr.commons;

import com.thomazllr.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerUtils {

    public List<Producer> createProducers() {
        String dateTime = "2025-08-02T20:17:28.425340394";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");

        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);

        var ufotable = Producer.builder().id(1L).name("ufotable").createdAt(localDateTime).build();
        var wit = Producer.builder().id(2L).name("wit").createdAt(localDateTime).build();
        var ghibli = Producer.builder().id(3L).name("ghibli").createdAt(localDateTime).build();
        return new ArrayList<>(List.of(ufotable, wit, ghibli));
    }

    public Producer createProducer() {
        return Producer.builder()
                .id(99L)
                .name("MAPPA")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
