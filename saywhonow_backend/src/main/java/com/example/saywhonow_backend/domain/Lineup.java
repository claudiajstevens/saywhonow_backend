package com.example.saywhonow_backend.domain;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.opencsv.bean.CsvBindByName;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
// import com.opencsv.bean.CsvDate;
// import jakarta.persistence.Basic;
// import jakarta.persistence.Table;

// @Table(name="lineups")
@Entity
public class Lineup {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CsvBindByName(column = "festival")
    @ManyToOne(fetch = FetchType.LAZY)  //(optional=false)
    @JoinColumn(name = "festival_id")
    @JsonIgnore
    private Festival festival;

    // @CsvBindByName(column = "artists")
    @OneToMany(mappedBy = "lineup", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LineupArtist> artists = new ArrayList<>();

    @Column(name = "start_date", columnDefinition = "DATE")
    @Temporal(value=TemporalType.DATE)
    private java.util.Date startDate;

    @CsvBindByName(column = "endDate")
    @Column(name="end_date", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private java.util.Date endDate;

    @Nullable
    @Lob
    @Column(name = "lineup_poster")
    private byte[] lineupPoster;

    @Nullable
    private boolean camping;
    // private List<Artist> artists = new ArrayList<>();
    

    public Lineup(){
        super();
    }

    public Lineup(Integer id, Date start, Date end){
        this.id = id;
        this.startDate = start;
        this.endDate = end;
    }

    public Lineup(Integer id, Festival festival){
        super();
        this.id = id;
        this.festival = festival;
    }

    public Lineup(Integer id, Festival festival, Date start, Date end){
        super();
        this.id = id;
        this.festival = festival;
        this.startDate = start;
        this.endDate = end;
    }

    public Lineup(Integer id, Festival festival, List<LineupArtist> artists){
        super();
        this.id = id;
        this.festival = festival;
        this.artists = artists;
    }

    public Lineup(Integer id, Festival festival, List<LineupArtist> artists, Date start, Date end){
        super();
        this.id = id;
        this.festival = festival;
        this.artists = artists;
        this.startDate = start;
        this.endDate = end;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Festival getFestival() {
        return festival;
    }
    
    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public List<LineupArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<LineupArtist> artists) {
        this.artists = artists;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public byte[] getLineupPoster() {
        return lineupPoster;
    }

    public void setLineupPoster(byte[] lineupPoster) {
        this.lineupPoster = lineupPoster;
    }

    public boolean isCamping() {
        return camping;
    }

    public void setCamping(boolean camping) {
        this.camping = camping;
    }
 
    
    
}
