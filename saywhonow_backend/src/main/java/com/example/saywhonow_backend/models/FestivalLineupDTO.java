package com.example.saywhonow_backend.models;

import java.util.Date;

public class FestivalLineupDTO {
    private Integer festivalId;
    private Date startDate;
    private Date endDate;

    
    public FestivalLineupDTO(Integer festivalId, Date startDate, Date endDate) {
        this.festivalId = festivalId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public Integer getFestivalId() {
        return festivalId;
    }
    public void setFestivalId(Integer festivalId) {
        this.festivalId = festivalId;
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

    @Override
    public String toString() {
        return "FestivalLineupDTO{" +
            "festivalId=" + festivalId +
            ", startDate='" + startDate + '\'' +
            ", endDate='" + endDate + '\'' +
            '}';
    }

    
}
