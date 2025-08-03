package com.thomazllr.repository;

import com.thomazllr.commons.UserUtils;
import com.thomazllr.data.UserData;
import com.thomazllr.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserHardCodedRepositoryTest {

    @InjectMocks
    private UserHardCodedRepository repository;

    @Mock
    private UserData userData;
    private List<User> usersList;

    @InjectMocks
    private UserUtils utils;

    @BeforeEach
    void init() {
        usersList = utils.createUsersList();
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list with all users")
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        BDDMockito.when(userData.getUsers()).thenReturn(usersList);
        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().hasSize(usersList.size());

    }

    @Test
    @Order(2)
    @DisplayName("findByID returns a object user with when id is present")
    void findById_ReturnsUser_WhenSuccessful() {
        BDDMockito.when(userData.getUsers()).thenReturn(usersList);
        var expectedUser = usersList.getFirst();
        var user = repository.findById(expectedUser.getId());
        Assertions.assertThat(user).isPresent().get().isEqualTo(expectedUser);

    }

    @Test
    @Order(3)
    @DisplayName("findByFirstName returns all user when name is null")
    void findByFirstName_ReturnEmptyList_WhenNameIsNull() {
        BDDMockito.when(userData.getUsers()).thenReturn(usersList);
        var users = repository.findByName(null);
        Assertions.assertThat(users).isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findByFirstName returns list with an user when name exist")
    void findByFirstName_ReturnListWithOneProducer_WhenNameIsFound() {
        BDDMockito.when(userData.getUsers()).thenReturn(usersList);
        var expectedUser = usersList.getFirst();
        var users = repository.findByName(expectedUser.getFirstName());
        Assertions.assertThat(users).contains(expectedUser);
    }


    @Test
    @Order(3)
    @DisplayName("save creates an user")
    void save_CreatesAUser_WhenSuccessful() {
        BDDMockito.when(userData.getUsers()).thenReturn(usersList);

        var userToBeSaved = utils.createUser();
        var user = repository.save(userToBeSaved);
        Assertions.assertThat(user).isNotNull().isEqualTo(userToBeSaved).hasNoNullFieldsOrProperties();
        var producerOptional = repository.findById(userToBeSaved.getId());
        Assertions.assertThat(producerOptional).isPresent().contains(userToBeSaved);


    }

}