package com.enigma.sepotipi.controller;
import com.enigma.sepotipi.entity.Artist;
import com.enigma.sepotipi.entity.Genre;
import com.enigma.sepotipi.services.ArtistServices;
import com.enigma.sepotipi.utility.FileReadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/artist")
public class ArtistController {

        @Autowired
        ArtistServices artistServices;

        @Autowired
        ObjectMapper objectMapper;

        @Autowired
        FileReadUtil fileReadUtil;

        @GetMapping("/search")
        public Page<Artist> searchArtist(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size, @RequestBody Artist searchForm){
            Pageable pageable = PageRequest.of(page-1, size);
            return artistServices.searchArtists(pageable,searchForm);
        }
        @GetMapping
        public Page<Artist> getAllArtists(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size){
            Pageable pageable = PageRequest.of(page-1,size);
            return artistServices.getAllArtists(pageable);
        }
    //    @PostMapping
    //    public Artist saveArtist(@RequestBody Artist artist){
    //        return artistServices.saveArtist(artist);
    //    }

    //    @GetMapping
    //    public Page<Artist> searchArtist(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
    //        Pageable pageable = PageRequest.of(page-1, size);
    //        return artistServices.getAllArtist(pageable);
    //    }

        @GetMapping("/{id}")
    public Artist getArtist(@PathVariable String id){
        return artistServices.getArtist(id);
    }

    @PostMapping("/form")
    public Artist saveArtistContainsImage(@RequestPart MultipartFile file, @RequestPart String formData) throws IOException {
        return artistServices.saveArtistFile(file,formData);
    }
    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable String id){
        artistServices.deleteArtist(id);
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity<Resource> getArtistPhotos(@PathVariable String id, HttpServletRequest request) throws IOException {
        Artist artist = artistServices.getArtist(id);
        if(artist==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        Resource resource = fileReadUtil.read(artist.getPhoto());
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
//BOkli