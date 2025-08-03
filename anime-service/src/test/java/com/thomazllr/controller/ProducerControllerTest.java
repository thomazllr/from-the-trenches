package com.thomazllr.controller;

import com.thomazllr.commons.FileUtils;
import com.thomazllr.commons.ProducerUtils;
import com.thomazllr.data.ProducerData;
import com.thomazllr.domain.Producer;
import com.thomazllr.repository.ProducerHardCodedRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.thomazllr")
@ActiveProfiles("test")
class ProducerControllerTest {

    private static final String URL = "/v1/producers";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProducerData producerData;
    private List<Producer> producerList;

    @MockitoSpyBean
    private ProducerHardCodedRepository repository;

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

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var response = fileUtils.readResourceFile("producer/get-producer-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(2)
    @DisplayName("GET v1/producers?name=Ufotable returns a list of producers when argument is null")
    void findAll_ReturnsFoundProducerInList_WhenNameIsFound() throws Exception {

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var response = fileUtils.readResourceFile("producer/get-producer-ufotable-name-200.json");
        var name = "ufotable";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(3)
    @DisplayName("GET v1/producers returns a list of producers when argument is null")
    void findAll_ReturnsAnEmptyList_WhenNameIsNotFound() throws Exception {

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

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

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var response = fileUtils.readResourceFile("producer/get-producer-by-id-200.json");
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @Order(5)
    @DisplayName("GET v1/producers/99 throws ResponseStatusException 404 when producer is not found")
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

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

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var request = fileUtils.readResourceFile("producer/put-request-producer-200.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }


    @Test
    @Order(8)
    @DisplayName("PUT v1/producers/99 update throws ResponseStatusException when producer is not found")
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var request = fileUtils.readResourceFile("producer/put-request-producer-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Producer Not Found"));

    }


    @Test
    @Order(9)
    @DisplayName("DELETE v1/producers/1 removes a producer")
    void delete_RemovesAProducer_WhenSuccessful() throws Exception {

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/producers/{id}", producerList.getFirst().getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @Order(10)
    @DisplayName("DELETE v1/producers/99 throws ResponseStatusException when producer is not found")
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/producers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Producer Not Found"));

    }


}