package com.thomazllr.repository;

import com.thomazllr.data.ProducersData;
import com.thomazllr.domain.Producer;
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

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodedRepositoryTest {

    @InjectMocks
    private ProducerHardCodedRepository repository;

    @Mock
    private ProducersData producersData;
    private final List<Producer> producerList = new ArrayList<>();


    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("ufotable").createdAt(LocalDateTime.now()).build();
        var wit = Producer.builder().id(1L).name("wit").createdAt(LocalDateTime.now()).build();
        var ghibli = Producer.builder().id(1L).name("ghibli").createdAt(LocalDateTime.now()).build();
        producerList.addAll(List.of(ufotable, wit, ghibli));
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list with all producers")
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        BDDMockito.when(producersData.getProducers()).thenReturn(producerList);
        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().hasSize(producerList.size());

    }

    @Test
    @Order(2)
    @DisplayName("findByID returns a object producer with when id")
    void findById_ReturnsProducer_WhenSuccessful() {
        BDDMockito.when(producersData.getProducers()).thenReturn(producerList);
        var expectedProducer = producerList.getFirst();
        var producer = repository.findById(expectedProducer.getId());
        Assertions.assertThat(producer).isPresent().get().isEqualTo(expectedProducer);

    }


    @Test
    @Order(3)
    @DisplayName("findByName returns all producers when name is null")
    void findByName_ReturnEmptyList_WhenNameIsNull() {
        BDDMockito.when(producersData.getProducers()).thenReturn(producerList);
        var producers = repository.findByName(null);
        Assertions.assertThat(producers).isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findByName returns list with producer when name exist")
    void findByName_ReturnListWithOneProducer_WhenNameIsFound() {
        BDDMockito.when(producersData.getProducers()).thenReturn(producerList);
        var expectedProducer = producerList.getFirst();
        var producers = repository.findByName(expectedProducer.getName());
        Assertions.assertThat(producers).contains(expectedProducer);
    }

}