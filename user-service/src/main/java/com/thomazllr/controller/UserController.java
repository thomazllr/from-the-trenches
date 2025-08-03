package com.thomazllr.controller;

import com.thomazllr.mapper.UserMapper;
import com.thomazllr.request.UserPostRequest;
import com.thomazllr.request.UserPutRequest;
import com.thomazllr.response.UserGetResponse;
import com.thomazllr.response.UserPostResponse;
import com.thomazllr.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserGetResponse>> findAll(@RequestParam(required = false) String firstName) {

        var userList = userService.findAll(firstName);

        var response = mapper.toUserGetResponseList(userList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetResponse> findUserById(@PathVariable long id) {

        var user = userService.findByIdOrThrowNotFound(id);
        var response = mapper.toResponse(user);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserPostResponse> save(@RequestBody @Valid UserPostRequest request) {
        var user = mapper.toEntity(request);
        var userSaved = userService.save(user);
        var response = mapper.toUserPostResponse(userSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        var user = userService.findByIdOrThrowNotFound(id);

        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        userService.delete(user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UserPutRequest request) {

        log.debug("Request to update user : {}", request);

        var user = mapper.toEntityFromUserPutRequest(request);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        userService.update(user);

        return ResponseEntity.noContent().build();
    }


}
