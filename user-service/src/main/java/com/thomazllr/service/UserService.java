package com.thomazllr.service;

import com.thomazllr.domain.User;
import com.thomazllr.repository.UserHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHardCodedRepository repository;

    public List<User> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public User findByIdOrThrowNotFound(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    public void delete(Long id) {
        var user = this.findByIdOrThrowNotFound(id);
        repository.delete(user);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void update(User userToUpdate) {
        assertAnimeExists(userToUpdate.getId());

        repository.update(userToUpdate);
    }

    public void assertAnimeExists(Long id) {
        findByIdOrThrowNotFound(id);
    }

}

