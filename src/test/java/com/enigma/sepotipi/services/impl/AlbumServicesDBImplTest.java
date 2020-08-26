package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Album;
import com.enigma.sepotipi.entity.Artist;
import com.enigma.sepotipi.entity.Song;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.AlbumRepository;
import com.enigma.sepotipi.repository.SongRepository;
import com.enigma.sepotipi.services.AlbumServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlbumServicesDBImplTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    AlbumServices albumServices;

    @Autowired
    SongRepository songRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void cleanUp(){
        albumRepository.deleteAll();
    }

    @Test
    void saveAlbum_shouldSaveOneAlbum_whenInvoked() {
        Album album = new Album();
        album.setTitle("sa");
        album.setDescription("be");
        album.setDiscount(23.2);
        album.setReleaseYear(4241);
        album = albumServices.saveAlbum(album);
        assertTrue(albumRepository.findAll().size()>0);
    }

    @Test
    void getAlbum_shouldGetSpecificID_whenCalled() {
        Album album = new Album();
        album.setTitle("sa");
        album.setDescription("be");
        album.setDiscount(23.2);
        album.setReleaseYear(4241);
        album = albumRepository.save(album);
        assertTrue(albumServices.getAlbum(album.getId())!=null);
    }

    @Test
    void getAlbum_shouldThrowException_whenSearchingWrongId(){
        assertThrows(ResourceNotFoundException.class, ()->{
            albumServices.getAlbum("cow");
        });
    }

    @Test
    void getAllAlbums_shouldReturn2Albums_whenInvoked() {
        Album album = new Album();
        album.setTitle("sa");
        album.setDescription("be");
        album.setDiscount(23.2);
        album.setReleaseYear(4241);
        album = albumRepository.save(album);

        Album album1 = new Album();
        album1.setTitle("sa");
        album1.setDescription("be");
        album1.setDiscount(23.2);
        album1.setReleaseYear(4241);
        album1 = albumRepository.save(album1);

        Page<Album> albums = albumServices.getAllAlbums(PageRequest.of(0,3));
        assertEquals(2, albums.getTotalElements());
    }

    @Test
    void searchAlbums() {
        Album album = new Album();
        album.setTitle("sa");
        album.setDescription("be");
        album.setDiscount(23.2);
        album.setReleaseYear(4241);
        albumRepository.save(album);

        Album album1 = new Album();
        album1.setTitle("sa");
        album1.setDescription("be");
        album1.setDiscount(23.2);
        album1.setReleaseYear(4241);
        albumRepository.save(album1);

        Song song = new Song();
        song.setTitle("Ha");
        song.setAlbum(album);
        songRepository.save(song);

        Song song1 = new Song();
        song1.setTitle("Ha");
        song1.setAlbum(album1);
        songRepository.save(song1);

        Page<Album> albums = albumServices.searchAlbums(PageRequest.of(0,2),new Album());
        assertEquals(2, albums.getTotalElements());
    }

    @Test
    void deleteAlbum() {
        Album album1 = new Album();
        album1.setTitle("sa");
        album1.setDescription("be");
        album1.setDiscount(23.2);
        album1.setReleaseYear(4241);
        album1 = albumRepository.save(album1);

        albumServices.deleteAlbum(album1.getId());
        assertFalse(albumRepository.findById(album1.getId()).isPresent());
    }

    @Test
    void saveAlbumFile() throws IOException {
        String requestBody = "{\"name\":\"mkyong\"}";
        Album album = albumServices.saveAlbum(objectMapper.readValue(requestBody, Album.class));
        MockMultipartFile file = new MockMultipartFile("filename", "filename.jpg", "jpg", "jpg".getBytes());
        albumServices.saveAlbumFile(file,requestBody);

    }
}