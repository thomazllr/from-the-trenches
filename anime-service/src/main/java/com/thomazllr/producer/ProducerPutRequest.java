package com.thomazllr.producer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProducerPutRequest {
    private long id;
    private String name;
}
