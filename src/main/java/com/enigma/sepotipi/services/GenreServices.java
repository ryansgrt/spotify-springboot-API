package com.enigma.sepotipi.services;

import com.enigma.sepotipi.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreServices {

    public Genre saveGenre(Genre genre);
    public Genre getGenre(String id);
    public List<Genre> getGenreList();
    public Page<Genre> getAllGenres(Pageable pageable);
    public void deleteGenre(String id);
}
