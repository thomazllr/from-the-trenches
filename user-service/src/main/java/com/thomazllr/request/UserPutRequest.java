package com.thomazllr.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPutRequest {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
