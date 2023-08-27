package com.example.saywhonow_backend.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="artists")
public class Artist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private List<Festival> festivals;
    private List<String> genres;

    public Artist(){
        super();
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

    public List<Festival> getFestivals() {
        return festivals;
    }

    public void addFestivals(Festival festival) {
        if( !this.festivals.contains(festival)){
            this.festivals.add(festival);
        }
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
