package com.thomazllr.service;

import com.thomazllr.commons.AnimeUtils;
import com.thomazllr.domain.Anime;
import com.thomazllr.repository.AnimeHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeHardCodedRepository repository;

    private List<Anime> animesList;

    @InjectMocks
    private AnimeUtils utils;

    @BeforeEach
    void init() {
        animesList = utils.createAnimes();
    }


    @Test
    @Order(1)
    @DisplayName("findAll returns a list of animes when argument is null")
    void findAll_ReturnsAAnimeList_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(animesList);
        var animes = service.findAll(null);
        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(animesList);
    }


    @Test
    @Order(2)
    @DisplayName("findAll returns a list found object when argument exists")
    void findAll_ReturnsFoundAnimeInList_WhenArgumentExists() {
        var anime = animesList.getFirst();
        var expectedAnimeFound = singletonList(anime);
        BDDMockito.when(repository.findByName(anime.getName())).thenReturn(expectedAnimeFound);
        var animes = service.findAll(anime.getName());
        Assertions.assertThat(animes).isNotNull().containsAll(expectedAnimeFound);
    }

    @Test
    @Order(3)
    @DisplayName("findAll returns an empty list when argument exists")
    void findAll_ReturnsAnEmptyList_WhenArgumentExists() {

        var argument = "not-found";
        BDDMockito.when(repository.findByName(argument)).thenReturn(emptyList());
        var animes = service.findAll(argument);
        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findByID returns a object anime with when id is present")
    void findById_OrThrowNotFound_ReturnsAnAnime_WhenSuccessful() {

        var expectedAnime = animesList.getFirst();

        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.of(expectedAnime));

        var anime = service.findByIdOrThrowNotFound(expectedAnime.getId());
        Assertions.assertThat(anime).isEqualTo(expectedAnime);

    }

    @Test
    @Order(5)
    @DisplayName("findByID throws ResponseStatus when anime is not found")
    void findById_OrThrowNotFound_ThrowsResponseStatusException_WhenAnimeIsNotFound() {

        var expectedAnime = animesList.getFirst();

        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedAnime.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @Order(6)
    @DisplayName("save creates an anime")
    void save_CreatesAnAnime_WhenSuccessful() {

        var animeToBeSaved = utils.createAnime();

        BDDMockito.when(repository.save(animeToBeSaved)).thenReturn(animeToBeSaved);

        var savedAnime = service.save(animeToBeSaved);
        Assertions.assertThat(savedAnime).isNotNull().isEqualTo(animeToBeSaved).hasNoNullFieldsOrProperties();

    }


    @Test
    @Order(7)
    @DisplayName("delete removes an anime")
    void delete_RemovesAnAnime_WhenSuccessful() {

        var animeToDelete = animesList.getFirst();
        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.of(animeToDelete));

        service.delete(animeToDelete.getId());

        BDDMockito.doNothing().when(repository).delete(animeToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(animeToDelete.getId()));

    }

    @Test
    @Order(8)
    @DisplayName("delete throws ResponseStatusException when anime is not found")
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var animeToDelete = animesList.getFirst();
        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(animeToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(9)
    @DisplayName("update an anime when successful")
    void update_UpdateAnAnime_WhenSuccessful() {

        var expectedAnimeToUpdate = animesList.getFirst();
        BDDMockito.when(repository.findById(expectedAnimeToUpdate.getId())).thenReturn(Optional.of(expectedAnimeToUpdate));
        BDDMockito.doNothing().when(repository).update(expectedAnimeToUpdate);
        Assertions.assertThatNoException().isThrownBy(() -> service.update(expectedAnimeToUpdate));

    }

    @Test
    @Order(10)
    @DisplayName("update throws ResponseStatusException when anime is not found")
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var expectedAnimeToUpdate = animesList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.update(expectedAnimeToUpdate))
                .isInstanceOf(ResponseStatusException.class);

    }


}