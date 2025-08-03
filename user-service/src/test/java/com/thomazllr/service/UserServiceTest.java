package com.thomazllr.service;

import com.thomazllr.commons.UserUtils;
import com.thomazllr.domain.User;
import com.thomazllr.repository.UserHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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


}