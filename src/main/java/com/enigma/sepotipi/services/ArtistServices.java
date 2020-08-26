package com.enigma.sepotipi.services;

import com.enigma.sepotipi.entity.Artist;
import com.enigma.sepotipi.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArtistServices {

    public Artist saveArtist(Artist artist);
    public Artist getArtist(String id);
    public Page<Artist> getAllArtist(Pageable pageable);
    public Page<Artist> searchArtists(Pageable pageable, Artist searchForm);
    public void deleteArtist(String id);
    public Artist saveArtistFile(MultipartFile multipartFile, String requestBody) throws IOException;
    public Page<Artist> getAllArtists(Pageable pageable);
}
