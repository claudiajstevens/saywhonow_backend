package com.example.saywhonow_backend.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.opencsv.bean.CsvBindByName;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="festivals")
public class Festival {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "country")
    private String country;

    @CsvBindByName(column = "state")
    private String state;

    @CsvBindByName(column = "city")
    private String city;

    @CsvBindByName(column = "monthHeld")
    private String monthHeld;

    private List<String> locations;
    private List<Integer> years;

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Lineup> lineup = new ArrayList<>();

    private List<String> genres = new ArrayList<>();
    
    @Transient
    private int length;

    public Festival(){
        super();
    }

    public Festival(Integer id, String name, String city, String state, String country, String monthHeld){
        super();
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.monthHeld = monthHeld;
        this.locations.add(String.join(", ", city, state, country));
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

    public List<String> getLocations() {
        return this.locations;
    }

    public void setLocations(String location) {
        this.locations.add(location);
    }

    public List<Lineup> getLineup() {
        return lineup;
    }

    public void setLineup(List<Lineup> lineup) {
        this.lineup = lineup;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void addYear(Integer year){
        this.years.add(year);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public String getMonthHeld() {
        return monthHeld;
    }

    public void setMonthHeld(String monthHeld) {
        this.monthHeld = monthHeld;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    

}
