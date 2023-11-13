package com.example.saywhonow_backend.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="artists")
public class Artist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<LineupArtist> lineupArtists = new ArrayList<>();
    
    private List<String> genres;

    public Artist(){
        super();
    }

    

    public Artist(String name) {
        this.name = name;
    }


    public Artist(Integer artistId, String name){
        super();
        this.id = artistId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<LineupArtist> getLineupArtists() {
        return lineupArtists;
    }

    public void setLineupArtists(List<LineupArtist> lineupArtists) {
        this.lineupArtists = lineupArtists;
    }

    
}
