package com.thomazllr.repository;

import com.thomazllr.data.AnimeData;
import com.thomazllr.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCodedRepositoryTest {

    @InjectMocks
    private AnimeHardCodedRepository animeHardCodedRepository;

    @Mock
    private AnimeData animeData;

    private List<Anime> animeList;

    @BeforeEach
    void init() {
        var naruto = Anime.builder().id(1L).name("Naruto").build();
        var dbz = Anime.builder().id(2L).name("DBZ").build();
        var bleach = Anime.builder().id(3L).name("Bleach").build();
        animeList = new ArrayList<>(List.of(naruto, dbz, bleach));
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list when successful")
    void findAll_ReturnsAnAnimeList_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animes = animeHardCodedRepository.findAll();
        Assertions.assertThat(animes).hasSameElementsAs(animeList).hasSize(animeList.size());
    }


    @Test
    @Order(2)
    @DisplayName("findByName returns an anime when successful")
    void findByName_ReturnsAnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var anime = animeList.getFirst();
        var animeFound = animeHardCodedRepository.findByName(anime.getName());
        Assertions.assertThat(animeFound).isNotNull().isEqualTo(anime);
    }

    @Test
    @Order(3)
    @DisplayName("findByName returns null when anime name is null")
    void findByName_ReturnsNull_WhenNotFound() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var result = animeHardCodedRepository.findByName(null);
        Assertions.assertThat(result).isNull();
    }

    @Test
    @Order(4)
    @DisplayName("findById returns an anime when successful")
    void findById_ReturnsAnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var anime = animeList.getFirst();
        var animeFound = animeHardCodedRepository.findById(anime.getId());
        Assertions.assertThat(animeFound).isNotNull().isEqualTo(anime);
    }

    @Test
    @Order(5)
    @DisplayName("save create an anime when successful")
    void save_CreateAnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeToBeSaved = Anime.builder().id(99L).name("Death Note").build();
        var animeSaved = animeHardCodedRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSaved).isEqualTo(animeToBeSaved).hasNoNullFieldsOrProperties();
        Assertions.assertThat(animeList).contains(animeSaved);
    }

    @Test
    @Order(6)
    @DisplayName("delete removes an anime when successful")
    void delete_RemoveAnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeToDelete = animeList.getFirst();
        animeHardCodedRepository.delete(animeToDelete);
        Assertions.assertThat(animeList).isNotEmpty().doesNotContain(animeToDelete);
    }

    @Test
    @Order(7)
    @DisplayName("update an anime when successful")
    void update_AnAnime_WhenSuccessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var animeToUpdate = animeList.getFirst();

        animeToUpdate.setName("Hellsing");

        animeHardCodedRepository.update(animeToUpdate);

        var anime = animeHardCodedRepository.findById(animeToUpdate.getId());

        Assertions.assertThat(anime).isEqualTo(animeToUpdate).hasNoNullFieldsOrProperties();
    }


}