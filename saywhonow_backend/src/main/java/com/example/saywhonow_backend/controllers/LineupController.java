package com.example.saywhonow_backend.controllers;

import java.io.IOException;
// import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.saywhonow_backend.domain.Festival;
import com.example.saywhonow_backend.domain.Lineup;
import com.example.saywhonow_backend.models.FestivalLineupDTO;
import com.example.saywhonow_backend.models.LineupDTO;
import com.example.saywhonow_backend.service.LineupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.RequestParam;
// import com.example.saywhonow_backend.domain.LineupArtist;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/lineup")
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

    @GetMapping(path = "{lineupId}/lineup-poster")
    public ResponseEntity<byte[]> getLineupImage(@PathVariable Integer lineupId){
        Optional<byte[]> lineupPoster = lineupService.getlineupPoster(lineupId);
        return lineupPoster.map(ResponseEntity::ok).orElseGet( () -> ResponseEntity.notFound().build());
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

    @PostMapping(path = "/{lineupId}/import-lineup-poster")
    public ResponseEntity<?> importLineupPoster(@PathVariable Integer lineupId, @RequestParam MultipartFile lineupPoster) throws IOException{
        lineupService.saveLineupPoster(lineupId, lineupPoster);
        return ResponseEntity.status(HttpStatus.OK).body("Lineup Poster uploaded successfully: ");
    }

    @DeleteMapping(path = "{lineupId}")
    public void deleteLineup(@PathVariable("lineupId") Integer lineupId){
        lineupService.deleteLineup(lineupId);
    }


}
