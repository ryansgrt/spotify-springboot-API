package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Artist;
import com.enigma.sepotipi.entity.Genre;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.ArtistRepository;
import com.enigma.sepotipi.services.ArtistServices;
import com.enigma.sepotipi.specification.ArtistSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ArtistServicesDBImpl implements ArtistServices {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public Artist getArtist(String id) {
        Artist artist = null;
        if(artistRepository.findById(id).isPresent()){
            artist = artistRepository.findById(id).get();
        } else throw new ResourceNotFoundException();
        return artist;
    }

    @Override
    public Page<Artist> getAllArtist(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }

    @Override
    public Page<Artist> searchArtists(Pageable pageable, Artist searchForm) {
        Page<Artist> artists = artistRepository
                .findAll(ArtistSpecification
                        .findByCriteria(searchForm),pageable);
        return artists;
    }

    @Override
    public void deleteArtist(String id) {
        artistRepository.deleteById(id);
    }

    @Override
    public Artist saveArtistFile(MultipartFile multipartFile, String requestBody) throws IOException {
        Artist artist = saveArtist(objectMapper.readValue(requestBody, Artist.class));
        File upload = new File("/home/riyan/" + artist.getId() + ".jpg");
        multipartFile.transferTo(upload);
        artist.setPhoto(upload.getPath());
        return artistRepository.save(artist);
    }

    @Override
    public Page<Artist> getAllArtists(Pageable pageable) {
        Page<Artist> artists = artistRepository.findAll(pageable);
        return artists;
    }
}
