package com.thomazllr.data;

import com.thomazllr.domain.User;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class UserData {

    private final List<User> users = new ArrayList<>(List.of(
            new User(1L, "Kakashi", "Hatake", "kakashi@email.com"),
            new User(2L, "Zoro", "Roronoa", "zoro@email.com"),
            new User(3L, "Luffy", "Monkey", "luffy@email.com")
    ));

}
