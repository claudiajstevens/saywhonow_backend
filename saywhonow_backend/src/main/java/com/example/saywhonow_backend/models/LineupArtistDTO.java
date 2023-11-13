package com.example.saywhonow_backend.models;

public class LineupArtistDTO {

    private String lineupArtist;
    private String day;
    private String stage;
    
    public LineupArtistDTO() {
    }

    public LineupArtistDTO(String lineupArtist, String day, String stage) {
        this.lineupArtist = lineupArtist;
        this.day = day;
        this.stage = stage;
    }

    public LineupArtistDTO(String linupArtist){
        this.lineupArtist = linupArtist;
    }

    public String getLineupArtist() {
        return lineupArtist;
    }

    public void setLineupArtist(String lineupArtist) {
        this.lineupArtist = lineupArtist;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "LineupArtistDTO{" + 
        "lineupArtist=" + lineupArtist + 
        ", day=" + day + 
        ", stage=" + stage +  "}";
    }
    
    
}
