package com.example.saywhonow_backend.models;

public class FestivalLineupPosterInfo {

    private String festivalName;
    private Integer year;

    
    public FestivalLineupPosterInfo() {
    }

    public FestivalLineupPosterInfo(String festivalName, Integer year) {
        this.festivalName = festivalName;
        this.year = year;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    
}
