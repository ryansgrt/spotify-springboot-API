package com.enigma.sepotipi.controller;

import com.enigma.sepotipi.entity.Album;
import com.enigma.sepotipi.entity.Song;
import com.enigma.sepotipi.services.AlbumServices;
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
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumServices albumServices;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FileReadUtil fileReadUtil;

    @GetMapping("/search")
    public Page<Album> searchAlbum(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size, @RequestBody Album search){
        Pageable pageable = PageRequest.of(page-1, size);
        return albumServices.searchAlbums(pageable, search);
    }

    @PostMapping("/form")
    public Album saveAlbum(@RequestPart MultipartFile file, @RequestPart String formData) throws IOException {
        return albumServices.saveAlbumFile(file, formData);
    }


//    @PostMapping
//    public Album saveAlbum(@RequestBody Album album){
//        return albumServices.saveAlbum(album);
//    }

    @GetMapping
    public Page<Album> getAllAlbums(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return albumServices.getAllAlbums(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable String id){
        albumServices.deleteAlbum(id);
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity<Resource> getAlbumPhotos(@PathVariable String id, HttpServletRequest request) throws IOException {
        Album album = albumServices.getAlbum(id);
        if(album==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        Resource resource = fileReadUtil.read(album.getImage());
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
