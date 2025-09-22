package com.thomazllr.controller;

import com.thomazllr.commons.FileUtils;
import com.thomazllr.commons.ProducerUtils;
import com.thomazllr.domain.Producer;
import com.thomazllr.anime.AnimeRepository;
import com.thomazllr.producer.ProducerController;
import com.thomazllr.producer.ProducerRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"com.thomazllr.producer", "com.thomazllr.commons"})
class ProducerControllerTest {

    private static final String URL = "/v1/producers";

    @Autowired
    private MockMvc mockMvc;

    private List<Producer> producerList;

    @MockitoBean
    private ProducerRepository repository;

    @MockitoBean
    private AnimeRepository animeRepository;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ProducerUtils producerUtils;


    @BeforeEach
    void init() {
        producerList = producerUtils.createProducers();
    }

    @Test
    @Order(1)
    @DisplayName("GET v1/producers returns a list of producers when argument is null")
    void findAll_ReturnsAProducerList_WhenArgumentIsNull() throws Exception {

        BDDMockito.when(repository.findAll()).thenReturn(producerList);

        var response = fileUtils.readResourceFile("producer/get-producer-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(2)
    @DisplayName("GET v1/producers?name=Ufotable returns a list of producers when argument is null")
    void findAll_ReturnsFoundProducerInList_WhenNameIsFound() throws Exception {


        var response = fileUtils.readResourceFile("producer/get-producer-ufotable-name-200.json");
        var name = "ufotable";

        var ufotable = producerList.stream().filter(producer -> producer.getName().equals(name)).findFirst().get();

        System.out.println(ufotable);

        BDDMockito.when(repository.findProducerByName(name)).thenReturn(Collections.singletonList(ufotable));


        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(3)
    @DisplayName("GET v1/producers returns a list of producers when argument is not found")
    void findAll_ReturnsAnEmptyList_WhenNameIsNotFound() throws Exception {

        BDDMockito.when(repository.findAll()).thenReturn(producerList);

        var response = fileUtils.readResourceFile("producer/get-producer-x-name-200.json");
        String name = "not-found";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @Order(4)
    @DisplayName("GET v1/producers/1 returns a object producer with when id is present")
    void findById_ReturnsAnProducer_WhenSuccessful() throws Exception {

        Long id = 1L;
        var producer = producerList.getFirst();

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(producer));

        var response = fileUtils.readResourceFile("producer/get-producer-by-id-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @Order(5)
    @DisplayName("GET v1/producers/99 throws NotFound 404 when producer is not found")
    void findById_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {

        BDDMockito.when(repository.findAll()).thenReturn(producerList);

        Long id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @Order(6)
    @DisplayName("POST v1/producers creates a producer")
    void save_CreatesProducer_WhenSuccessful() throws Exception {

        var request = fileUtils.readResourceFile("producer/post-request-producer-200.json");

        var response = fileUtils.readResourceFile("producer/post-response-producer-201.json");

        var producerToBeSaved = producerUtils.createProducer();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToBeSaved);


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
    @DisplayName("PUT v1/producers/1 update a producer")
    void update_UpdatesProducer_WhenSuccessful() throws Exception {

        var producer = producerList.getFirst();
        var id = producer.getId();
        producer.setName("aniplex");

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producer));
        BDDMockito.when(repository.save(producer)).thenReturn(producer);

        var request = fileUtils.readResourceFile("producer/put-request-producer-200.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }


    @Test
    @Order(8)
    @DisplayName("PUT v1/producers/99 update throws NotFound when producer is not found")
    void update_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {

        BDDMockito.when(repository.findAll()).thenReturn(producerList);

        var request = fileUtils.readResourceFile("producer/put-request-producer-404.json");
        var response = fileUtils.readResourceFile("producer/put-producer-by-id-404.json");


        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @Order(9)
    @DisplayName("DELETE v1/producers/1 removes a producer")
    void delete_RemovesAProducer_WhenSuccessful() throws Exception {

        var producer = producerList.getFirst();
        var id = producer.getId();

        BDDMockito.given(repository.findById(id)).willReturn(Optional.of(producer));

        BDDMockito.willDoNothing().given(repository).delete(producer);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/producers/{id}", producerList.getFirst().getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @Order(10)
    @DisplayName("DELETE v1/producers/99 throws NotFound when producer is not found")
    void delete_ThrowsNotFound_WhenProducerIsmNotFound() throws Exception {

        BDDMockito.when(repository.findAll()).thenReturn(producerList);

        var response = fileUtils.readResourceFile("producer/delete-producer-by-id-404.json");

        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/producers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }


}