package com.example.saywhonow_backend.controllers;

import java.util.Date;
import java.util.List;

import com.example.saywhonow_backend.domain.Festival;
import com.example.saywhonow_backend.domain.Lineup;
import com.example.saywhonow_backend.domain.LineupArtist;
import com.example.saywhonow_backend.service.LineupService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lineup")
@CrossOrigin("*")
public class LineupController {
    private final LineupService lineupService;

    public LineupController(LineupService lineupService){
        this.lineupService = lineupService;
    }


    //maybe change this to get lineup by id??
    @GetMapping
    public Lineup getLineup(String festival){
        return lineupService.getLineup(festival);
    }

    @PostMapping("/add")
    public Lineup registerNewLineup(Festival festival, Date start, Date end){
        return lineupService.addNewLineup(festival, start, end);
    }

    @DeleteMapping(path = "{lineupId}")
    public void deleteLineup(@PathVariable("lineupId") Integer lineupId){
        lineupService.deleteLineup(lineupId);
    }
}

