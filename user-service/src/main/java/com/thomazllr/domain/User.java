package com.thomazllr.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}
