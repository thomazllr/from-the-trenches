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

}