package com.thomazllr.producer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProducerGetResponse {

    private long id;
    private String name;
    private LocalDateTime createdAt;
}
