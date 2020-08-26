package com.enigma.sepotipi.controller;

import com.enigma.sepotipi.entity.Genre;
import com.enigma.sepotipi.services.GenreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    GenreServices genreServices;

    @PostMapping("/form")
    public Genre saveGenre(@RequestBody Genre genre){
        return  genreServices.saveGenre(genre);
    }

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable String id){
        return genreServices.getGenre(id);
    }

    @GetMapping
    public Page<Genre> getAllGenres(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return genreServices.getAllGenres(pageable);
    }

//    @GetMapping
//    public List<Genre> getListOfGenre(){
//        return genreServices.getGenreList();
//    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable String id){
        genreServices.deleteGenre(id);
    }

}
