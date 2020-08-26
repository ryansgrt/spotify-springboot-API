package com.enigma.sepotipi.controller;

import com.enigma.sepotipi.entity.Song;
import com.enigma.sepotipi.services.SongServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    SongServices songServices;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/form")
    public Song saveSong(@RequestBody Song song){
        return songServices.saveSong(song);
    }

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable String id){
        return songServices.getSong(id);
    }

    @GetMapping
    public Page<Song> getAllSongs(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return songServices.getAllSongs(pageable);
    }

    @GetMapping("/search")
    public Page<Song> searchSongs(@RequestBody Song search,@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return songServices.searchSongs(pageable, search);
    }
    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable String id){
        songServices.deleteSong(id);
    }
}
