package com.thomazllr.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
