package com.example.saywhonow_backend.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.example.saywhonow_backend.domain.LineupArtist;
import com.example.saywhonow_backend.models.FestivalLineupDTO;
import com.example.saywhonow_backend.models.LineupArtistDTO;
import com.example.saywhonow_backend.service.LineupArtistService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/{linuepId}")
    public List<LineupArtist> getLineupArtistsInFestivalYear(@PathVariable("lineupId") Integer lineupId){
        return lineupArtistService.getLineupArtistInFestivalYear(lineupId);
    }

    @PostMapping("/add")
    public LineupArtist registerNewLineupArtist(String lineupArtist){
        return lineupArtistService.addNewLineupArtist(lineupArtist);
    }

    @PostMapping("/import-lineupArtists/{lineupId}")
    public String importLineups(@RequestBody List<LineupArtistDTO> lineupArtistDTOs, @PathVariable("lineupId") Integer lineupId){
        System.out.println("In Lineup Controller /import-lineupArtists");
        for( LineupArtistDTO lineupArtistDTO : lineupArtistDTOs){
            System.out.println(lineupArtistDTO.toString());
        }
        try{
            lineupArtistService.importLineupArtists(lineupArtistDTOs, lineupId);
            return "Festival lineups uploaded successfully";
        }catch (Exception e){
            e.printStackTrace();
            return "Error importing lineups: " + e.getMessage();
        }
    }

    @PutMapping(path = "{lineupArtistName}")
    public void updateLineupArtist(
        @PathVariable("lineupArtistId") Integer lineupArtistId,
        @RequestParam(required = false) Date day,
        @RequestParam(required = false) String stage
    ){
        lineupArtistService.updateLineupArtist(lineupArtistId, day, stage);
    }
}
