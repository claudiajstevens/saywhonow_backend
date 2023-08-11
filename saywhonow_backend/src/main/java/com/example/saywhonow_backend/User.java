package com.example.saywhonow_backend;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    private long id;
    private String username;
    private String password;
    private List<Festival> festivals = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Festival> getFestivals() {
        return festivals;
    }

    public void setFestivals(List<Festival> festivals) {
        this.festivals = festivals;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Artist> getArtists() {
        return artists;
    }
    
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
