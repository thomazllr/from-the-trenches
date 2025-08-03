package com.thomazllr.service;

import com.thomazllr.commons.UserUtils;
import com.thomazllr.domain.User;
import com.thomazllr.repository.UserHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserHardCodedRepository repository;

    private List<User> usersList;

    @InjectMocks
    private UserUtils utils;

    @BeforeEach
    void init() {
        usersList = utils.createUsersList();
    }


    @Test
    @Order(1)
    @DisplayName("findAll returns a list of users when argument is null")
    void findAll_ReturnsAUserList_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(usersList);
        var users = service.findAll(null);
        Assertions.assertThat(users).isNotNull().hasSameElementsAs(usersList);
    }


    @Test
    @Order(2)
    @DisplayName("findAll returns a list found object when argument exists")
    void findAll_ReturnsFoundUserInList_WhenArgumentExists() {
        var user = usersList.getFirst();
        var expectedUserFound = singletonList(user);
        BDDMockito.when(repository.findByName(user.getFirstName())).thenReturn(expectedUserFound);
        var users = service.findAll(user.getFirstName());
        Assertions.assertThat(users).isNotNull().containsAll(expectedUserFound);
    }

    @Test
    @Order(3)
    @DisplayName("findAll returns an empty list when argument exists")
    void findAll_ReturnsAnEmptyList_WhenArgumentExists() {

        var argument = "not-found";
        BDDMockito.when(repository.findByName(argument)).thenReturn(emptyList());
        var users = service.findAll(argument);
        Assertions.assertThat(users).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findByID returns an user user with when id is present")
    void findById_OrThrowNotFound_ReturnsAnUser_WhenSuccessful() {

        var expectedUser = usersList.getFirst();

        BDDMockito.when(repository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));

        var user = service.findByIdOrThrowNotFound(expectedUser.getId());
        Assertions.assertThat(user).isEqualTo(expectedUser);

    }


    @Test
    @Order(6)
    @DisplayName("save creates an user")
    void save_CreatesAnUser_WhenSuccessful() {

        var userToBeSaved = utils.createUser();

        BDDMockito.when(repository.save(userToBeSaved)).thenReturn(userToBeSaved);

        var savedUser = service.save(userToBeSaved);
        Assertions.assertThat(savedUser).isNotNull().isEqualTo(userToBeSaved).hasNoNullFieldsOrProperties();

    }


    @Test
    @Order(5)
    @DisplayName("findByID throws ResponseStatus when user is not found")
    void findById_OrThrowNotFound_ThrowsResponseStatusException_WhenUserIsNotFound() {

        var user = usersList.getFirst();

        BDDMockito.when(repository.findById(user.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(user.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @Order(7)
    @DisplayName("delete removes an user")
    void delete_RemovesAnUser_WhenSuccessful() {

        var userToDelete = usersList.getFirst();
        BDDMockito.when(repository.findById(userToDelete.getId())).thenReturn(Optional.of(userToDelete));

        service.delete(userToDelete.getId());

        BDDMockito.doNothing().when(repository).delete(userToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(userToDelete.getId()));

    }

    @Test
    @Order(8)
    @DisplayName("delete throws ResponseStatusException when user is not found")
    void delete_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var userToDelete = usersList.getFirst();
        BDDMockito.when(repository.findById(userToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(userToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(9)
    @DisplayName("update an user when successful")
    void update_UpdateAnUser_WhenSuccessful() {

        var expectedUserToUpdate = usersList.getFirst();
        BDDMockito.when(repository.findById(expectedUserToUpdate.getId())).thenReturn(Optional.of(expectedUserToUpdate));
        BDDMockito.doNothing().when(repository).update(expectedUserToUpdate);
        Assertions.assertThatNoException().isThrownBy(() -> service.update(expectedUserToUpdate));

    }

    @Test
    @Order(10)
    @DisplayName("update throws ResponseStatusException when user is not found")
    void update_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var expectedUserToUpdate = usersList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.update(expectedUserToUpdate))
                .isInstanceOf(ResponseStatusException.class);

    }



}