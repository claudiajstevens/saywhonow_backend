package com.example.saywhonow_backend.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.example.saywhonow_backend.domain.LineupArtist;
import com.example.saywhonow_backend.service.LineupArtistService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/lineupArtist")
@CrossOrigin("*")
public class LineupArtistController {
    private final LineupArtistService lineupArtistService;

    public LineupArtistController(LineupArtistService lineupArtistService){
        this.lineupArtistService = lineupArtistService;
    }

    @GetMapping
    public List<LineupArtist> getLineupArtists(){
        return lineupArtistService.getLineupArtists();
    }

    @PostMapping("/add")
    public LineupArtist registerNewLineupArtist(String lineupArtist){
        return lineupArtistService.addNewLineupArtist(lineupArtist);
    }

    @PostMapping("/import-lineup-artists")
    public String importLineupArtists(@RequestParam("csvFile") MultipartFile csvFile){
        try {
            List<LineupArtist> lineupArtists = lineupArtistService.readLineupArtistsFromCSV(csvFile);

            lineupArtistService.saveLineupArtists(lineupArtists);

            return "Lineup artists imported successfully";
        } catch (IOException e){
            e.printStackTrace();
            return "Error importing lineup artists: " + e.getMessage();
        }
    }

    @PutMapping(path = "{lineupArtistName}")
    public void updateLineupArtist(
        @PathVariable("lineupArtistName") String lineupArtist,
        @RequestParam(required = false) Date day,
        @RequestParam(required = false) String stage
    ){
        lineupArtistService.updateLineupArtist(lineupArtist, day, stage);
    }
}
