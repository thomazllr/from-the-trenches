package com.thomazllr.controller;

import com.thomazllr.commons.AnimeUtils;
import com.thomazllr.data.AnimeData;
import com.thomazllr.domain.Anime;
import com.thomazllr.repository.AnimeHardCodedRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@WebMvcTest(controllers = AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.thomazllr")
class AnimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnimeData animeData;
    private List<Anime> animesList;

    @MockitoSpyBean
    private AnimeHardCodedRepository repository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AnimeUtils utils;

    @BeforeEach
    void init() {
        animesList = utils.createAnimes();
    }

    @Test
    @Order(1)
    @DisplayName("GET v1/animes returns a list of animes when argument is null")
    void findAll_ReturnsAAnimeList_WhenArgumentIsNull() throws Exception {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var response = readResourceFile("anime/get-anime-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(2)
    @DisplayName("GET v1/animes?name=hunter x hunter returns a list of animes when argument is null")
    void findAll_ReturnsFoundAnimeInList_WhenNameIsFound() throws Exception {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var response = readResourceFile("anime/get-anime-hxh-name-200.json");
        var name = "hunter x hunter";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(3)
    @DisplayName("GET v1/animes returns a list of animes when argument is not found")
    void findAll_ReturnsAnEmptyList_WhenNameIsNotFound() throws Exception {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var response = readResourceFile("anime/get-anime-x-name-200.json");
        String name = "not-found";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @Order(4)
    @DisplayName("GET v1/animes/1 returns a object anime with when id is present")
    void findById_ReturnsAnAnime_WhenSuccessful() throws Exception {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var response = readResourceFile("anime/get-anime-by-id-200.json");
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @Order(5)
    @DisplayName("GET v1/animes/99 throws ResponseStatusException 404 when anime is not found")
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var response = readResourceFile("anime/get-anime-by-id-404.json");

        Long id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @Order(6)
    @DisplayName("POST v1/animes creates a anime")
    void save_CreatesAnime_WhenSuccessful() throws Exception {

        var request = readResourceFile("anime/post-request-anime-200.json");

        var response = readResourceFile("anime/post-response-anime-201.json");

        var animeToBeSaved = utils.createAnime();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(animeToBeSaved);


        mockMvc.perform(MockMvcRequestBuilders.post("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @Order(7)
    @DisplayName("PUT v1/animes/1 update a anime")
    void update_UpdatesAnime_WhenSuccessful() throws Exception {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var request = readResourceFile("anime/put-request-anime-200.json");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }


    @Test
    @Order(8)
    @DisplayName("PUT v1/animes/99 update throws ResponseStatusException when anime is not found")
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var request = readResourceFile("anime/put-request-anime-404.json");
        var response = readResourceFile("anime/put-anime-by-id-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }


    @Test
    @Order(9)
    @DisplayName("DELETE v1/animes/1 removes a anime")
    void delete_RemovesAAnime_WhenSuccessful() throws Exception {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", animesList.getFirst().getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @Order(10)
    @DisplayName("DELETE v1/animes/99 throws ResponseStatusException when anime is not found")
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {

        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);

        var response = readResourceFile("anime/delete-anime-by-id-404.json");

        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }


    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }


}