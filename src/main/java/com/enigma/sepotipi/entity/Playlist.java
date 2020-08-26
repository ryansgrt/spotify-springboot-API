package com.enigma.sepotipi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mst_playlist")
public class Playlist {

    @Id
    @GeneratedValue(generator = "artist_uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "artist_uuid", strategy = "uuid")
    private String id;
    private String name;
    private Boolean isPublic;

    @ManyToMany
    @JoinTable(name = "playlist_has_songs",
    joinColumns = @JoinColumn(name = "playlist_id"),
    inverseJoinColumns = @JoinColumn(name = "song_id"))
    @JsonIgnoreProperties(value = "playlists")
    private List<Song> songs;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account author;

    @Transient
    private String accountId;

    public Playlist() {
    }

    public Playlist(String id, String name, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(id, playlist.id) &&
                Objects.equals(name, playlist.name) &&
                Objects.equals(isPublic, playlist.isPublic) &&
                Objects.equals(author, playlist.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isPublic, author);
    }
}
