package com.thomazllr.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPostRequest {

    @NotBlank(message = "The field 'firstName' is required")
    private String firstName;

    @NotBlank(message = "The field 'lastName' is required")
    private String lastName;

    @NotBlank(message = "The field 'email' is required")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The field 'email' must be a valid email")
    private String email;
}
