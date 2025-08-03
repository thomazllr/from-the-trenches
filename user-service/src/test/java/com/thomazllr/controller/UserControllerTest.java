package com.thomazllr.controller;

import com.thomazllr.commons.FileUtils;
import com.thomazllr.commons.UserUtils;
import com.thomazllr.data.UserData;
import com.thomazllr.domain.User;
import com.thomazllr.repository.UserHardCodedRepository;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(controllers = UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.thomazllr")
class UserControllerTest {


    private static final String URL = "/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserData userData;
    private List<User> userList;

    @MockitoSpyBean
    private UserHardCodedRepository repository;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private UserUtils userUtils;


    @BeforeEach
    void init() {
        userList = userUtils.createUsersList();
    }

    @Test
    @Order(1)
    @DisplayName("GET v1/users returns a list of users when argument is null")
    void findAll_ReturnsAUserList_WhenArgumentIsNull() throws Exception {

        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var response = fileUtils.readResourceFile("user/get-user-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(2)
    @DisplayName("GET v1/users?firstName=Gohan returns a list of users when argument is null")
    void findAll_ReturnsFoundUserInList_WhenNameIsFound() throws Exception {

        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var response = fileUtils.readResourceFile("user/get-user-gohan-name-200.json");
        var firstName = "Gohan";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", firstName))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(3)
    @DisplayName("GET v1/users returns a list of users when argument is not found")
    void findAll_ReturnsAnEmptyList_WhenNameIsNotFound() throws Exception {

        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var response = fileUtils.readResourceFile("user/get-user-x-name-200.json");
        String firstName = "not-found";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", firstName))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @Order(4)
    @DisplayName("GET v1/users/1 returns a object user with when id is present")
    void findById_ReturnsAnUser_WhenSuccessful() throws Exception {

        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var response = fileUtils.readResourceFile("user/get-user-by-id-200.json");
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @Order(5)
    @DisplayName("GET v1/users/99 throws ResponseStatusException 404 when user is not found")
    void findById_ThrowsResponseStatusException_WhenUserIsNotFound() throws Exception {

        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        Long id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());

    }


}