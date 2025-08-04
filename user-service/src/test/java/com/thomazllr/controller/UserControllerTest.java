package com.thomazllr.controller;

import com.thomazllr.commons.FileUtils;
import com.thomazllr.commons.UserUtils;
import com.thomazllr.domain.User;
import com.thomazllr.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(controllers = UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.thomazllr")
class UserControllerTest {

    private static final String URL = "/v1/users";

    @Autowired
    private MockMvc mockMvc;

    private List<User> userList;

    @MockitoBean
    private UserRepository repository;

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

        BDDMockito.when(repository.findAll()).thenReturn(userList);

        var response = fileUtils.readResourceFile("user/get-user-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(2)
    @DisplayName("GET v1/users?firstName=Gohan returns a list of users when argument is null")
    void findAll_ReturnsFoundUserInList_WhenNameIsFound() throws Exception {

        var response = fileUtils.readResourceFile("user/get-user-gohan-name-200.json");
        var firstName = "Gohan";

        var gohan = userList.stream().filter(user -> user.getFirstName().equalsIgnoreCase(firstName)).findFirst().orElse(null);

        BDDMockito.when(repository.findByFirstNameIgnoreCase(firstName)).thenReturn(Collections.singletonList(gohan));


        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", firstName))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(3)
    @DisplayName("GET v1/users returns a list of users when argument is not found")
    void findAll_ReturnsAnEmptyList_WhenNameIsNotFound() throws Exception {


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

        var response = fileUtils.readResourceFile("user/get-user-by-id-200.json");

        Long id = 1L;

        var foundUser = userList.stream().filter(user -> user.getId().equals(id)).findFirst();

        BDDMockito.when(repository.findById(id)).thenReturn(foundUser);


        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @Order(5)
    @DisplayName("GET v1/users/99 throws ResponseStatusException 404 when user is not found")
    void findById_ThrowsResponseStatusException_WhenUserIsNotFound() throws Exception {


        Long id = 9999L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());

    }


    @Test
    @Order(6)
    @DisplayName("POST v1/users creates a user")
    void save_CreatesUser_WhenSuccessful() throws Exception {

        var request = fileUtils.readResourceFile("user/post-request-user-200.json");

        var response = fileUtils.readResourceFile("user/post-response-user-201.json");

        var userToBeSaved = userUtils.createUser();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(userToBeSaved);


        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }


    @Test
    @Order(7)
    @DisplayName("PUT v1/users/1 update a user")
    void update_UpdatesUser_WhenSuccessful() throws Exception {

        var request = fileUtils.readResourceFile("user/put-request-user-200.json");

        Long id = 1L;

        var foundUser = userList.stream().filter(user -> user.getId().equals(id)).findFirst();

        BDDMockito.when(repository.findById(id)).thenReturn(foundUser);

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }


    @Test
    @Order(8)
    @DisplayName("PUT v1/users/99 update throws ResponseStatusException when user is not found")
    void update_ThrowsResponseStatusException_WhenUserIsNotFound() throws Exception {

        var request = fileUtils.readResourceFile("user/put-request-user-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User Not Found"));

    }


    @Test
    @Order(9)
    @DisplayName("DELETE v1/users/1 removes a user")
    void delete_RemovesAUser_WhenSuccessful() throws Exception {

        Long id = 1L;

        var foundUser = userList.stream().filter(user -> user.getId().equals(id)).findFirst();

        BDDMockito.when(repository.findById(id)).thenReturn(foundUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/users/{id}", userList.getFirst().getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @Order(10)
    @DisplayName("DELETE v1/users/99 throws ResponseStatusException when user is not found")
    void delete_ThrowsResponseStatusException_WhenUserIsNotFound() throws Exception {


        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/users/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User Not Found"));

    }

    @ParameterizedTest
    @MethodSource("postUserBadRequestSource")
    @Order(11)
    @DisplayName("POST v1/users returns a BadRequest when fields are empty")
    void save_ReturnsBadRequest_WhenFieldsAreEmpty(String fileName, List<String> errors) throws Exception {

        var request = fileUtils.readResourceFile("user/%s".formatted(fileName));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Exception resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }


    private static Stream<Arguments> postUserBadRequestSource() {

        var firstRequiredNameError = "The field 'firstName' is required";
        var lastRequiredNameError = "The field 'lastName' is required";
        var emailRequiredError = "The field 'email' is required";
        var emailInvalidError = "The field 'email' must be a valid email";

        var allErrors = List.of(firstRequiredNameError, lastRequiredNameError, emailRequiredError);
        var emailError = Collections.singletonList(emailInvalidError);

        return Stream.of(
                Arguments.of("post-request-user-empty-fields-400.json", allErrors),
                Arguments.of("post-request-user-blank-fields-400.json", allErrors),
                Arguments.of("post-request-user-invalid-email-400.json", emailError)
        );
    }


}