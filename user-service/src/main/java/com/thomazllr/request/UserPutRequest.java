package com.thomazllr.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPutRequest {

    @NotNull(message = "The field 'id' is required")
    private long id;

    @NotBlank(message = "The field 'firstName' is required")
    private String firstName;

    @NotBlank(message = "The field 'lastName' is required")
    private String lastName;

    @NotBlank(message = "The field 'email' is required")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The field 'email' must be a valid email")
    private String email;

}
