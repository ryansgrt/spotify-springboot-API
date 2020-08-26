package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Album;
import com.enigma.sepotipi.entity.Artist;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.AlbumRepository;
import com.enigma.sepotipi.services.AlbumServices;
import com.enigma.sepotipi.specification.AlbumSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AlbumServicesDBImpl implements AlbumServices {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public Album getAlbum(String id) {
        Album album = null;
        if(albumRepository.findById(id).isPresent()){
            album = albumRepository.findById(id).get();
        } else throw new ResourceNotFoundException();
        return album;
    }

    @Override
    public Page<Album> getAllAlbums(Pageable pageable) {
        Page<Album> albums = albumRepository.findAll(pageable);
        return albums;
    }

    @Override
    public Page<Album> searchAlbums(Pageable pageable, Album searchForm) {
        Page<Album> albums = albumRepository.findAll(AlbumSpecification.findByCriteria(searchForm), pageable);
        return albums;
    }

    @Override
    public void deleteAlbum(String id) {
        albumRepository.deleteById(id);

    }

    @Override
    public Album saveAlbumFile(MultipartFile multipartFile, String requestBody) throws IOException {
        Album album = saveAlbum(objectMapper.readValue(requestBody, Album.class));
        File upload = new File("/home/riyan/" + album.getId() + ".jpg");
        multipartFile.transferTo(upload);
        album.setImage(upload.getPath());
        return albumRepository.save(album);
    }
}
