package com.enigma.sepotipi.services;

import com.enigma.sepotipi.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AlbumServices {
    public Album saveAlbum(Album album);
    public Album getAlbum(String id);
    public Page<Album> getAllAlbums(Pageable pageable);
    public Page<Album> searchAlbums(Pageable pageable, Album searchForm);
    public void deleteAlbum(String id);
    public Album saveAlbumFile(MultipartFile multipartFile, String requestBody) throws IOException;
}
