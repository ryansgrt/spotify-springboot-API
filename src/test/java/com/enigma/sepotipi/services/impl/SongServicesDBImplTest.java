package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Album;
import com.enigma.sepotipi.entity.Artist;
import com.enigma.sepotipi.entity.Genre;
import com.enigma.sepotipi.entity.Song;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.AlbumRepository;
import com.enigma.sepotipi.repository.ArtistRepository;
import com.enigma.sepotipi.repository.GenreRepository;
import com.enigma.sepotipi.repository.SongRepository;
import com.enigma.sepotipi.services.SongServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SongServicesDBImplTest {

    @Autowired
    SongRepository songRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    SongServices songServices;

    @BeforeEach
    public void cleanUp(){
        songRepository.deleteAll();
    }

    @Test
    void saveSong_shouldSaveOneSong_whenInvoked() {

        Song song = new Song();
        song.setTitle("Haha");
        song.setDuration(2);
        song.setPrice(2.0);
        song.setReleaseYear(20);
        song = songServices.saveSong(song);
        assertEquals(1, songRepository.findAll().size());
    }

    @Test
    void saveSong_shouldSaveCorrectValues_whenInvoked(){
        Song song = new Song();
        song.setTitle("Haha");
        song.setDuration(2);
        song.setPrice(2.0);
        song.setReleaseYear(20);
        song = songServices.saveSong(song);
        Song song1 = songRepository.findById(song.getId()).get();
        assertTrue(song.equals(song1));
    }

    @Test
    void getSong_shouldGetSpecificSong_whenInvoked() {
        Song song = new Song();
        song.setTitle("Haha");
        song.setDuration(2);
        song.setPrice(2.0);
        song.setReleaseYear(20);
        song = songRepository.save(song);
        Song find = songServices.getSong(song.getId());
        assertTrue(song.equals(find));
    }

    @Test
    void getSong_shouldThrowException_whenInvoked(){
        assertThrows(ResourceNotFoundException.class, ()->{
            songServices.getSong("Bambang");
        });
    }

    @Test
    void getAllSongs_shouldReturn2Songs_whenInvoked() {
        Song song = new Song();
        song.setTitle("Haha");
        song.setDuration(2);
        song.setPrice(2.0);
        song.setReleaseYear(20);
        song = songRepository.save(song);

        Song song1 = new Song();
        song1.setTitle("Hehe");
        song1.setDuration(2);
        song1.setPrice(2.0);
        song1.setReleaseYear(20);
        song1 = songRepository.save(song1);
        Page<Song> songs = songServices.getAllSongs(PageRequest.of(0,2));
        assertEquals(2, songs.getTotalElements());
    }

    @Test
    void searchSongs_shouldReturn2Songs_whenCallTheSameValues() {
        Genre genre = new Genre();
        genre.setName("Hi");
        genreRepository.save(genre);

        Album album = new Album();
        album.setTitle("Hm");
        albumRepository.save(album);

        Artist artist = new Artist();
        artist.setName("Ho");
        artistRepository.save(artist);

        Song song = new Song();
        song.setTitle("Ha");
        song.setDuration(2);
        song.setPrice(2.0);
        song.setReleaseYear(20);
        song.setGenre(genre);
        song.setAlbum(album);
        song.setArtist(artist);
        songRepository.save(song);

        Song song1 = new Song();
        song1.setTitle("Ha");
        song1.setDuration(2);
        song1.setPrice(2.0);
        song1.setReleaseYear(20);
        song1.setGenre(genre);
        song1.setAlbum(album);
        song1.setArtist(artist);
        songRepository.save(song1);
        Page<Song>songs = songServices.searchSongs(PageRequest.of(0,2), new Song());
        assertEquals(2, songs.getTotalElements());
    }

    @Test
    void deleteSong_shouldReturnFalse_whenSearchingDeletedID() {

        Song song = new Song();
        song.setTitle("Ha");
        song.setDuration(2);
        song.setPrice(2.0);
        song.setReleaseYear(20);
        songRepository.save(song);
        songServices.deleteSong(song.getId());
        assertFalse(songRepository.findById(song.getId()).isPresent());
    }
}