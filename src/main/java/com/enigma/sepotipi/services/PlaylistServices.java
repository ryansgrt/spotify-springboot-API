package com.enigma.sepotipi.services;

import com.enigma.sepotipi.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaylistServices {

    public Playlist savePlaylist(Playlist playlist);
    public Playlist getPlaylist(String id);
    public Page<Playlist> searchPlaylists(Pageable pageable, Playlist searchForm);
    public void deletePlaylist(String id);
}
