package com.thomazllr.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Producer {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

}
