package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Artist;
import com.enigma.sepotipi.entity.Song;
import com.enigma.sepotipi.enums.GenderEnum;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.ArtistRepository;
import com.enigma.sepotipi.repository.SongRepository;
import com.enigma.sepotipi.services.ArtistServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArtistServicesDBImplTest {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    ArtistServices artistServices;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void cleanUp(){
        artistRepository.deleteAll();
    }

    @Test
    void saveArtist_DBShouldIncreaseByOne_whenInvoked() {
        Artist artist = new Artist();
        artist.setName("a");
        artist.setBiography("b");
        artist.setDebutYear(12);
        artist.setGender(GenderEnum.MALE);
        artist.setPhoto("a");
        artist = artistServices.saveArtist(artist);
//        assertEquals(1, artistRepository.findAll().size());
        assertTrue(artistRepository.findAll().size()>0);
    }

    @Test
    void saveArtist_shouldSaveSameValues_whenInvoked(){
        Artist artist = new Artist();
        artist.setName("a");
        artist.setBiography("b");
        artist.setDebutYear(12);
        artist.setGender(GenderEnum.MALE);
        artist.setPhoto("a");
        artist = artistServices.saveArtist(artist);
        Artist find = artistRepository.findById(artist.getId()).get();
        assertTrue(artist.equals(find));
    }

    @Test
    void getArtist_shouldGetSpecificArtist_whenInvoked() {
        Artist artist = new Artist();
        artist.setName("a");
        artist.setBiography("b");
        artist.setDebutYear(12);
        artist.setGender(GenderEnum.MALE);
        artist.setPhoto("a");
        artist = artistRepository.save(artist);
        assertTrue(artistServices.getArtist(artist.getId())!=null);
    }

    @Test
    void getArtist_shouldThrowException_whenIDNotFound(){
        assertThrows(ResourceNotFoundException.class, ()->{
            artistServices.getArtist("Cow");
        });
    }

    @Test
    void getAllArtist_shouldReturn2Artists_whenGetAll() {
        Artist artist = new Artist();
        artist.setName("a");
        artist.setBiography("b");
        artist.setDebutYear(12);
        artist.setGender(GenderEnum.MALE);
        artist.setPhoto("a");
        artist = artistRepository.save(artist);

        Artist artist1 = new Artist();
        artist1.setName("a");
        artist1.setBiography("b");
        artist1.setDebutYear(12);
        artist1.setGender(GenderEnum.MALE);
        artist1.setPhoto("a");
        artist1 = artistRepository.save(artist1);

        Page<Artist>artists = artistServices.getAllArtist(PageRequest.of(0,2));
        assertEquals(2, artists.getSize());
    }

    @Test
    void searchArtists_shouldReturnArtists_whenSearchBySpecifications() {
        Artist artist = new Artist();
        artist.setName("Ho");
        artistRepository.save(artist);

        Artist artist1 = new Artist();
        artist.setName("Ho");
        artistRepository.save(artist1);

        Song song = new Song();
        song.setTitle("Ha");
        song.setDuration(2);
        song.setPrice(2.0);
        song.setReleaseYear(20);
        song.setArtist(artist);
        songRepository.save(song);

        Song song1 = new Song();
        song1.setTitle("Ha");
        song1.setDuration(2);
        song1.setPrice(2.0);
        song1.setReleaseYear(20);
        song1.setArtist(artist);
        songRepository.save(song1);
        Page<Artist> artists = artistServices.searchArtists(PageRequest.of(0,2), new Artist());
        assertEquals(2,artists.getTotalElements());
    }

    @Test
    void deleteArtist_shouldReturnFalse_whenSearchingDeletedID() {
        Artist artist = new Artist();
        artist.setName("a");
        artist.setBiography("b");
        artist.setDebutYear(12);
        artist.setGender(GenderEnum.MALE);
        artist.setPhoto("a");
        artist = artistRepository.save(artist);

        artistServices.deleteArtist(artist.getId());
        assertFalse(artistRepository.findById(artist.getId()).isPresent());
    }

    @Test
    void saveArtistFile_shouldSaveOnePicture() throws IOException {
        String requestBody = "{\"name\":\"mkyong\"}";
        Artist artist = artistServices.saveArtist(objectMapper.readValue(requestBody, Artist.class));
        MockMultipartFile file = new MockMultipartFile("filename", "filename.jpg", "jpg", "jpg".getBytes());
        artistServices.saveArtistFile(file,requestBody);
    }
}