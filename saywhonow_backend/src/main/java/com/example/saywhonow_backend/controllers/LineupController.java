package com.example.saywhonow_backend.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.example.saywhonow_backend.domain.Festival;
import com.example.saywhonow_backend.domain.Lineup;
import com.example.saywhonow_backend.domain.LineupArtist;
import com.example.saywhonow_backend.models.FestivalLineupDTO;
import com.example.saywhonow_backend.models.LineupDTO;
import com.example.saywhonow_backend.service.LineupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/all")
    public List<Lineup> getAllLineups(){
        return lineupService.getAllLineups();
    }

    @GetMapping(path = "/{festivalId}")
    public List<Lineup> getLineupsByFestival(@PathVariable("festivalId") Integer festivalId ){
        System.out.println("In lineup controller /{festivalId}");
        System.out.println(festivalId);
        return lineupService.getLineupsFromFestival(festivalId);
    }

    @GetMapping(path = "/upcoming-lineups")
    public List<LineupDTO> getUpcomingLineups() {
        return lineupService.getUpcomingLineups();
    }

    @PostMapping("/add")
    public Lineup registerNewLineup(Festival festival, Date start, Date end){
        return lineupService.addNewLineup(festival, start, end);
    }

    @PostMapping("/import-lineups")
    public String importLineups(@RequestBody List<FestivalLineupDTO> festivalLineupDTO){
        System.out.println("In Lineup Controller /import-lineups");
        // for( FestivalLineupDTO lineupDTO : festivalLineupDTO){
        //     System.out.println(lineupDTO.toString());
        // }
        // System.out.println(festivalLineupDTO);
        try{
            lineupService.importFestivalLineups(festivalLineupDTO);
            return "Festival lineups uploaded successfully";
        }catch (Exception e){
            e.printStackTrace();
            return "Error importing lineups: " + e.getMessage();
        }
    }

    @DeleteMapping(path = "{lineupId}")
    public void deleteLineup(@PathVariable("lineupId") Integer lineupId){
        lineupService.deleteLineup(lineupId);
    }
}
