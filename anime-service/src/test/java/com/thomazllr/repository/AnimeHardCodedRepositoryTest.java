package com.thomazllr.repository;

import com.thomazllr.commons.AnimeUtils;
import com.thomazllr.data.AnimeData;
import com.thomazllr.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCodedRepositoryTest {

    @InjectMocks
    private AnimeHardCodedRepository animeHardCodedRepository;

    @Mock
    private AnimeData animeData;

    private List<Anime> animesList;

    @InjectMocks
    private AnimeUtils utils;

    @BeforeEach
    void init() {
        animesList = utils.createAnimes();
    }


    @Test
    @Order(1)
    @DisplayName("findAll returns a list when successful")
    void findAll_ReturnsAnAnimeList_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var animes = animeHardCodedRepository.findAll();
        Assertions.assertThat(animes).hasSameElementsAs(animesList).hasSize(animesList.size());
    }


    @Test
    @Order(2)
    @DisplayName("findByName returns a list with an anime when successful")
    void findByName_ReturnsAListWithAnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var anime = animesList.getFirst();
        var animeFound = animeHardCodedRepository.findByName(anime.getName());
        Assertions.assertThat(animeFound)
                .hasSize(1)
                .contains(anime);
    }

    @Test
    @Order(3)
    @DisplayName("findByName returns null when anime name is null")
    void findByName_ReturnsNull_WhenNotFound() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var result = animeHardCodedRepository.findByName(null);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById returns a list with one anime when successful")
    void findById_ReturnsAnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var anime = animesList.getFirst();
        var animeFound = animeHardCodedRepository.findById(anime.getId());
        Assertions.assertThat(animeFound)
                .isPresent()
                .get()
                .isIn(animesList);
    }

    @Test
    @Order(5)
    @DisplayName("save create an anime when successful")
    void save_CreateAnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var animeToBeSaved = utils.createAnime();
        var animeSaved = animeHardCodedRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSaved).isEqualTo(animeToBeSaved).hasNoNullFieldsOrProperties();
        Assertions.assertThat(animesList).contains(animeSaved);
    }

    @Test
    @Order(6)
    @DisplayName("delete removes an anime when successful")
    void delete_RemoveAnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var animeToDelete = animesList.getFirst();
        animeHardCodedRepository.delete(animeToDelete);
        Assertions.assertThat(animesList).isNotEmpty().doesNotContain(animeToDelete);
    }

    @Test
    @Order(7)
    @DisplayName("update an anime when successful")
    void update_AnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animesList);
        var animeToUpdate = animesList.getFirst();

        animeToUpdate.setName("Hellsing");

        animeHardCodedRepository.update(animeToUpdate);

        var anime = animeHardCodedRepository.findById(animeToUpdate.getId()).orElse(null);

        Assertions.assertThat(anime).isEqualTo(animeToUpdate).hasNoNullFieldsOrProperties();
    }


}