package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Song;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.SongRepository;
import com.enigma.sepotipi.services.SongServices;
import com.enigma.sepotipi.specification.SongSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SongServicesDBImpl implements SongServices {

    @Autowired
    SongRepository songRepository;

    @Override
    public Song saveSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public Song getSong(String id) {
        Song song = null;
        if(songRepository.findById(id).isPresent()){
            song = songRepository.findById(id).get();
        } else throw new ResourceNotFoundException();
        return song;
    }

    @Override
    public Page<Song> getAllSongs(Pageable pageable) {
        Page<Song> songs = songRepository.findAll(pageable);
        return songs;
    }

    @Override
    public Page<Song> searchSongs(Pageable pageable, Song searchForm) {
        Page<Song> songs = songRepository.findAll(SongSpecification.findByCriteria(searchForm),pageable);
        return songs;
    }

    @Override
    public void deleteSong(String id) {
        songRepository.deleteById(id);
    }
}
