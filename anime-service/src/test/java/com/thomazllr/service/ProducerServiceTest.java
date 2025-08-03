package com.thomazllr.service;

import com.thomazllr.commons.ProducerUtils;
import com.thomazllr.domain.Producer;
import com.thomazllr.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerHardCodedRepository repository;

    private List<Producer> producerList;

    @InjectMocks
    private ProducerUtils utils;

    @BeforeEach
    void init() {
        producerList = utils.createProducers();
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

    @Test
    @Order(4)
    @DisplayName("findByID returns a object producer with when id is present")
    void findById_ReturnsAnProducer_WhenSuccessful() {

        var expectedProducer = producerList.getFirst();

        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));

        var producer = service.findById(expectedProducer.getId());
        Assertions.assertThat(producer).isEqualTo(expectedProducer);

    }

    @Test
    @Order(5)
    @DisplayName("findByID throws ResponseStatus when producer is not found")
    void findById_ThrowsResponseStatusException_WhenProducerIsNotFound() {

        var expectedProducer = producerList.getFirst();

        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findById(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @Order(6)
    @DisplayName("save creates a producer")
    void save_CreatesAProducer_WhenSuccessful() {

        var producerToBeSaved = utils.createProducer();

        BDDMockito.when(repository.save(producerToBeSaved)).thenReturn(producerToBeSaved);

        var savedProducer = service.save(producerToBeSaved);
        Assertions.assertThat(savedProducer).isNotNull().isEqualTo(producerToBeSaved).hasNoNullFieldsOrProperties();

    }


    @Test
    @Order(7)
    @DisplayName("delete removes a producer")
    void delete_RemovesAProducer_WhenSuccessful() {

        var producerToDelete = producerList.getFirst();
        BDDMockito.when(repository.findById(producerToDelete.getId())).thenReturn(Optional.of(producerToDelete));

        service.delete(producerToDelete.getId());

        BDDMockito.doNothing().when(repository).delete(producerToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(producerToDelete.getId()));

    }

    @Test
    @Order(8)
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var producerToDelete = producerList.getFirst();
        BDDMockito.when(repository.findById(producerToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(producerToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(9)
    @DisplayName("update a producer when successful")
    void update_UpdateAProducer_WhenSuccessful() {

        var expectedProducerToUpdate = producerList.getFirst();
        BDDMockito.when(repository.findById(expectedProducerToUpdate.getId())).thenReturn(Optional.of(expectedProducerToUpdate));
        BDDMockito.doNothing().when(repository).update(expectedProducerToUpdate);
        Assertions.assertThatNoException().isThrownBy(() -> service.update(expectedProducerToUpdate));

    }

    @Test
    @Order(10)
    @DisplayName("update throws ResponseStatusException when producer is not found")
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound() {

        var expectedProducerToUpdate = producerList.getFirst();


        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(expectedProducerToUpdate))
                .isInstanceOf(ResponseStatusException.class);

    }


}