package com.enigma.sepotipi.controller;
import com.enigma.sepotipi.entity.Playlist;
import com.enigma.sepotipi.services.PlaylistServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/account/playlist")
public class PlaylistController {
    @Autowired
    PlaylistServices playlistServices;
    @PostMapping
    public Playlist savePlaylist(@RequestBody Playlist playlist){
        return playlistServices.savePlaylist(playlist);
    }
    @GetMapping("/{id}")
    public Playlist getPlaylist(@PathVariable String id){
        return playlistServices.getPlaylist(id);
    }
    @GetMapping("/search")
    public Page<Playlist> getAllPlaylist(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size, @RequestBody Playlist playlist){
        Pageable pageable = PageRequest.of(page-1, size);
        return playlistServices.searchPlaylists(pageable, playlist);
    }
    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable String id){
        playlistServices.deletePlaylist(id);
    }
}