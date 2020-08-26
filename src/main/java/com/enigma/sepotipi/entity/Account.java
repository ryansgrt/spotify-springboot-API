package com.enigma.sepotipi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mst_account")
public class Account {

    @Id
    @GeneratedValue(generator = "artist_uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "artist_uuid", strategy = "uuid")
    private String id;
    private Boolean isActive;

    @OneToMany(mappedBy = "author")
//    @JsonIgnoreProperties(value = "author")
    @JsonIgnore
    private List<Playlist> playlists;

    @OneToOne(mappedBy = "account")
    @JsonIgnoreProperties(value = "account")
    private Profile profile;

    @OneToOne(mappedBy = "owner")
    @JsonIgnoreProperties(value = "owner")
    private Wallet wallet;

    @Transient
    private String profileId;

    public Account() {
    }

    public Account(String id, Boolean isActive) {
        this.id = id;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(isActive, account.isActive) &&
                Objects.equals(profile, account.profile) &&
                Objects.equals(wallet, account.wallet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isActive, profile, wallet);
    }
}
