package com.thomazllr.service;

import com.thomazllr.domain.User;
import com.thomazllr.exception.NotFoundException;
import com.thomazllr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByFirstNameIgnoreCase(name);
    }

    public User findByIdOrThrowNotFound(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    public void delete(Long id) {
        var user = this.findByIdOrThrowNotFound(id);
        repository.delete(user);
    }

    public User save(User user) {
        assertEmailDoesNotExist(user.getEmail());
        return repository.save(user);
    }

    public void update(User userToUpdate) {
        assertEmailDoesNotExist(userToUpdate.getEmail(),userToUpdate.getId());
        repository.save(userToUpdate);
    }

    public void assertUserExists(Long id) {
        findByIdOrThrowNotFound(id);
    }

    public void assertEmailDoesNotExist(String email) {
        repository.findByEmail(email).ifPresent(this::throwEmailAlreadyExists);
    }

    public void assertEmailDoesNotExist(String email, Long id) {
        repository.findByEmailAndIdNot(email, id).ifPresent(this::throwEmailAlreadyExists);
    }

    private void throwEmailAlreadyExists(User user) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email: '%s' already exists".formatted(user.getEmail()));
    }

}

