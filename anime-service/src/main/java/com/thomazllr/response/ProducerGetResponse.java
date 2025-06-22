package com.thomazllr.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProducerGetResponse {

    private long id;
    private String name;
    private LocalDateTime createdAt;
}
