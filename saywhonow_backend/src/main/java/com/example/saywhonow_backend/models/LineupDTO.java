package com.example.saywhonow_backend.models;

import java.util.Date;

public class LineupDTO {
    private Integer lineupId;
    private Date startDate;
    private Date endDate;
    private String festival;

    public LineupDTO() {
    }


    public LineupDTO(Integer lineupId, Date startDate, Date endDate, String festival) {
        this.lineupId = lineupId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.festival = festival;
    }


    public Integer getLineupId() {
        return lineupId;
    }

    public void setLineupId(Integer lineupId) {
        this.lineupId = lineupId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setDate(Date date) {
        this.startDate = date;
    }

    
    public String getFestival() {
        return festival;
    }

    public void setFestival(String festival) {
        this.festival = festival;
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

    
}
