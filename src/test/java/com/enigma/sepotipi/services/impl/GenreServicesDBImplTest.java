package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Genre;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.GenreRepository;
import com.enigma.sepotipi.services.GenreServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenreServicesDBImplTest {

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    GenreServices genreServices;

    @BeforeEach
    public void cleanUp(){
        genreRepository.deleteAll();
    }

    @Test
    void saveGenre_shouldCreateOneGenre_whenSaved() {
        Genre genre = new Genre();
        genre.setName("Rock");
        genreServices.saveGenre(genre);
        assertEquals(1,genreRepository.findAll().size());
    }

    @Test
    void saveGenre_shouldSave_CorrectValues_whenSaved(){
        Genre genre = new Genre();
        genre.setName("Rock");
        genre = genreServices.saveGenre(genre);
        Genre actual = genreRepository.findById(genre.getId()).get();
        assertEquals(genre, actual);
    }

    @Test
    void getGenre_shouldGetSpecificIDGenre_whenInvoked() {
        Genre genre = new Genre();
        genre.setName("Rock");
        genreServices.saveGenre(genre);
        Genre find = genreServices.getGenre(genre.getId());
        assertEquals(find, genre);
    }

    @Test
    void getGenre_shouldThrowException_whenIDNotFound(){
        assertThrows(ResourceNotFoundException.class, ()->{
            genreServices.getGenre("Gerard");
        });
    }

    @Test
    void getAllGenres_shouldReturn2Genres_whenInvoked() {
        Genre genre = new Genre();
        genre.setName("Rock");
        genre = genreRepository.save(genre);
        Genre genre1 = new Genre();
        genre1.setName("Rockers");
        genre1 = genreRepository.save(genre1);
        Page<Genre> genres = genreServices.getAllGenres(PageRequest.of(0,2));
        assertEquals(2, genres.getTotalElements());
    }

    @Test
    void deleteGenre_shouldReturnFalse_whenFindDeletedID() {
        Genre genre = new Genre();
        genre.setName("Rock");
        genre = genreRepository.save(genre);
        genreServices.deleteGenre(genre.getId());
        assertFalse(genreRepository.findById(genre.getId()).isPresent());
    }
}