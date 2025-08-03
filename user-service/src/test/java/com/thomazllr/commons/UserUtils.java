package com.thomazllr.commons;

import com.thomazllr.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {

    public List<User> createUsersList() {
        var goku = User.builder().id(1L).firstName("Goku").lastName("Kakarato").email("goku@gmail").build();
        var gohan = User.builder().id(2L).firstName("Gohan").lastName("Son").email("gohan@gmail").build();
        var trunks = User.builder().id(3L).firstName("Trunks").lastName("").email("trunks@gmail").build();
        return new ArrayList<>(List.of(goku, gohan, trunks));
    }

    public User createUser() {
        return User.builder()
                .id(99L)
                .firstName("Naruto")
                .lastName("Uzumaki")
                .email("uzumaki@gmail")
                .build();
    }
}
