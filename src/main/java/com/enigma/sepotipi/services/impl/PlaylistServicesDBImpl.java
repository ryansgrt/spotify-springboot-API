package com.enigma.sepotipi.services.impl;
import com.enigma.sepotipi.entity.Account;
import com.enigma.sepotipi.entity.Playlist;
import com.enigma.sepotipi.entity.Song;
import com.enigma.sepotipi.entity.Transaction;
import com.enigma.sepotipi.repository.PlaylistRepository;
import com.enigma.sepotipi.services.AccountServices;
import com.enigma.sepotipi.services.PlaylistServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class PlaylistServicesDBImpl implements PlaylistServices {
    @Autowired
    PlaylistRepository playlistRepository;
    @Autowired
    AccountServices accountServices;
    @Override
    public Playlist savePlaylist(Playlist playlist) {
        Account author = accountServices.getAccount(playlist.getAccountId());
        playlist.setAuthor(author);
        List<Song> songList = new ArrayList<Song>();
        List<Song> songs = playlist.getSongs();
        for(Song song: songs){
            List<Transaction> transactions = author.getWallet().getTransactions();
            for(Transaction transaction : transactions){
                if(song.getId().equals(transaction.getItem().getId())){
                    System.out.println("Song can be added to playlist.");
                    songList.add(song);
                    break;
                }
            }
        }
        playlist.setSongs(songList);
        return playlistRepository.save(playlist);
    }
    @Override
    public Playlist getPlaylist(String id) {
        Playlist playlist = null;
        if(playlistRepository.findById(id).isPresent()){
            playlist = playlistRepository.findById(id).get();
        }
        return playlist;
    }
    @Override
    public Page<Playlist> searchPlaylists(Pageable pageable, Playlist searchForm) {
        return playlistRepository.findAll(null, pageable);
    }
    @Override
    public void deletePlaylist(String id) {
        playlistRepository.deleteById(id);
    }
}