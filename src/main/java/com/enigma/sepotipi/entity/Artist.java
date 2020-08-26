package com.enigma.sepotipi.entity;

import com.enigma.sepotipi.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mst_artist")
public class Artist {

    @Id
    @GeneratedValue(generator = "artist_uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "artist_uuid", strategy = "uuid")
    private String id;

    private String name;

    private Integer debutYear;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String biography;

    private String photo;
    @OneToMany(mappedBy = "artist")
    @JsonIgnoreProperties(value = "artist")
    private List<Song> songs;

    public Artist() {
    }

    public Artist(String id, String name, Integer debutYear, String biography, String photo) {
        this.id = id;
        this.name = name;
        this.debutYear = debutYear;
        this.biography = biography;
        this.photo = photo;
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

    public Integer getDebutYear() {
        return debutYear;
    }

    public void setDebutYear(Integer debutYear) {
        this.debutYear = debutYear;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id) &&
                Objects.equals(name, artist.name) &&
                Objects.equals(debutYear, artist.debutYear) &&
                gender == artist.gender &&
                Objects.equals(biography, artist.biography) &&
                Objects.equals(photo, artist.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, debutYear, gender, biography, photo);
    }
}
