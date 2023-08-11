package com.example.saywhonow_backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Lineup {
    private Long id;
    private Festival festival;
    private Date date;
    private List<Artist> artists = new ArrayList<>();
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Festival getFestival() {
        return festival;
    }
    
    public void setFestival(Festival festival) {
        this.festival = festival;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public List<Artist> getArtists() {
        return artists;
    }
    
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
