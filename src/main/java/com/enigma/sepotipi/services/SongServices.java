package com.enigma.sepotipi.services;

import com.enigma.sepotipi.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SongServices {

    public Song saveSong(Song song);
    public Song getSong(String id);
    public Page<Song> getAllSongs(Pageable pageable);
    public Page<Song> searchSongs(Pageable pageable, Song searchForm);
    public void deleteSong(String id);
}
