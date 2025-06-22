package com.thomazllr.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Producer {

    private long id;
    private String name;
    private LocalDateTime createdAt;

}
