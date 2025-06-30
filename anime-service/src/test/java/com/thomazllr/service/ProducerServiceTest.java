package com.thomazllr.service;

import com.thomazllr.domain.Producer;
import com.thomazllr.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerHardCodedRepository repository;

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
    void findAll_ReturnsAProducerList_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(producerList);
        var producers = service.findAll(null);
        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);
    }


    @Test
    @Order(2)
    @DisplayName("findAll returns a list found object when argument exists")
    void findAll_ReturnsFoundProducerInList_WhenArgumentExists() {
        var producer = producerList.getFirst();
        var expectedProducerFound = singletonList(producer);
        BDDMockito.when(repository.findByName(producer.getName())).thenReturn(expectedProducerFound);
        var producers = service.findAll(producer.getName());
        Assertions.assertThat(producers).isNotNull().containsAll(expectedProducerFound);
    }

    @Test
    @Order(3)
    @DisplayName("findAll returns a list found object when argument exists")
    void findAll_ReturnsAnEmptyList_WhenArgumentExists() {

        var argument = "not-found";
        BDDMockito.when(repository.findByName(argument)).thenReturn(emptyList());
        var producers = service.findAll(argument);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

}