package com.thomazllr.repository;

import com.thomazllr.data.UserData;
import com.thomazllr.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserHardCodedRepository {

    private final UserData userData;

    public List<User> findAll() {
        return userData.getUsers();
    }

    public List<User> findByName(String name) {
        return userData.getUsers().stream().filter(user -> user.getFirstName().equalsIgnoreCase(name)).toList();
    }

    public Optional<User> findById(long id) {
        return userData.getUsers().stream().filter(user -> user.getId() == id).findFirst();
    }

    public void delete(User user) {
        userData.getUsers().remove(user);
    }

    public User save(User user) {
        userData.getUsers().add(user);
        return user;
    }

    public void update(User user) {
        userData.getUsers().remove(user);
        userData.getUsers().add(user);
    }

}
