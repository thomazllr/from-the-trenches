package com.thomazllr.controller;

import com.thomazllr.data.ProducerData;
import com.thomazllr.domain.Producer;
import com.thomazllr.mapper.ProducerMapperImpl;
import com.thomazllr.repository.ProducerHardCodedRepository;
import com.thomazllr.service.ProducerService;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({ProducerMapperImpl.class, ProducerService.class, ProducerHardCodedRepository.class, ProducerData.class})
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProducerData producerData;
    private List<Producer> producerList;

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("ufotable").createdAt(LocalDateTime.now()).build();
        var wit = Producer.builder().id(2L).name("wit").createdAt(LocalDateTime.now()).build();
        var ghibli = Producer.builder().id(3L).name("ghibli").createdAt(LocalDateTime.now()).build();
        producerList = new ArrayList<>(List.of(ufotable, wit, ghibli));
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list of producers when argument is null")
    void findAll_ReturnsAProducerList_WhenArgumentIsNull() throws Exception {

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1L));
    }


}