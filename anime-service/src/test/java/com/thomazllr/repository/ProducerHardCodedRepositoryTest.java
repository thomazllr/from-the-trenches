package com.thomazllr.repository;

import com.thomazllr.data.ProducerData;
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
    @DisplayName("findAll returns a list with all producers")
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().hasSize(producerList.size());

    }

    @Test
    @Order(2)
    @DisplayName("findByID returns a object producer with when id is present")
    void findById_ReturnsProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducer = producerList.getFirst();
        var producer = repository.findById(expectedProducer.getId());
        Assertions.assertThat(producer).isPresent().get().isEqualTo(expectedProducer);

    }


    @Test
    @Order(3)
    @DisplayName("findByName returns all producers when name is null")
    void findByName_ReturnEmptyList_WhenNameIsNull() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producers = repository.findByName(null);
        Assertions.assertThat(producers).isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findByName returns list with producer when name exist")
    void findByName_ReturnListWithOneProducer_WhenNameIsFound() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducer = producerList.getFirst();
        var producers = repository.findByName(expectedProducer.getName());
        Assertions.assertThat(producers).contains(expectedProducer);
    }

    @Test
    @Order(5)
    @DisplayName("save creates a producer")
    void save_CreatesAProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var producerToBeSaved = Producer.builder()
                .id(99L)
                .name("MAPPA")
                .createdAt(LocalDateTime.now())
                .build();

        var producer = repository.save(producerToBeSaved);
        Assertions.assertThat(producer).isNotNull().isEqualTo(producerToBeSaved).hasNoNullFieldsOrProperties();
        var producerOptional = repository.findById(producerToBeSaved.getId());
        Assertions.assertThat(producerOptional).isPresent().contains(producerToBeSaved);


    }

    @Test
    @Order(6)
    @DisplayName("delete remove a producer")
    void delete_Remove_WhenSucessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducerToDelete = producerList.getFirst();
        repository.delete(expectedProducerToDelete);
        var producers = repository.findAll();
        Assertions.assertThat(producers).isNotEmpty().doesNotContain(expectedProducerToDelete);

    }


    @Test
    @Order(7)
    @DisplayName("update a producer")
    void update_UpdateAProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var expectedProducerToUpdate = producerList.getFirst();
        expectedProducerToUpdate.setName("Animeplex");
        repository.update(expectedProducerToUpdate);
        Assertions.assertThat(producerList).contains(expectedProducerToUpdate);
        var producerOptional = repository.findById(expectedProducerToUpdate.getId());

        Assertions.assertThat(producerOptional).isPresent();
        Assertions.assertThat(producerOptional.get().getName()).isEqualTo(expectedProducerToUpdate.getName());


    }


}