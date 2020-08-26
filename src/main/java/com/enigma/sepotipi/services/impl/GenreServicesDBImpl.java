package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Genre;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.GenreRepository;
import com.enigma.sepotipi.services.GenreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServicesDBImpl implements GenreServices {

    @Autowired
    GenreRepository genreRepository;

    @Override
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre getGenre(String id) {
        Genre genre = null;
        if(genreRepository.findById(id).isPresent()){
            genre = genreRepository.findById(id).get();
        } else throw new ResourceNotFoundException();
        return genre;
    }

    public List<Genre> getGenreList(){
        return genreRepository.findAll();
    }

    @Override
    public Page<Genre> getAllGenres(Pageable pageable) {
        Page<Genre> genres = genreRepository.findAll(pageable);
        return genres;
    }

    @Override
    public void deleteGenre(String id) {
        genreRepository.deleteById(id);
    }
}
