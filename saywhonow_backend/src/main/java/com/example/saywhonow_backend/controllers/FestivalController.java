package com.example.saywhonow_backend.controllers;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.util.Date;

import com.example.saywhonow_backend.domain.Festival;
import com.example.saywhonow_backend.service.FestivalService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/festivals")
@CrossOrigin("*")
public class FestivalController {
    private final FestivalService festivalService;

    public FestivalController(FestivalService festivalService){
        this.festivalService = festivalService;
    }

    @GetMapping
    public List<Festival> getFestivals(){
        return festivalService.getFestivals();
    }

    @PostMapping("/add")
    public Festival registerNewFestival(String festivalName, String city, String state, String country, String monthHeld){
        return festivalService.addNewFestival(festivalName, city, state, country, monthHeld);
    }

    @PostMapping("/import-festivals")
    public String importFestivals(@RequestParam("csvFile") MultipartFile csvFile){
        try {
            List<Festival> festivals = festivalService.readFestivalsFromCSV(csvFile);

            // saving the festivals to the database
            festivalService.saveFestivals(festivals);

            return "Festivals imported successfully";
        } catch (IOException e){
            e.printStackTrace();
            return "Error importing festivals: " + e.getMessage();
        }
    }

    @GetMapping(path = "/{festivalId}")
    public Festival getFestival(@PathVariable("festivalId") Integer festivalId){
        System.out.println("In festival service");
        return festivalService.getFestivalById(festivalId);
    }

    @DeleteMapping(path = "{festivalId}")
    public void deleteFestival(@PathVariable("festivalId") Integer festivalId){
        festivalService.deleteFestival(festivalId);
    }


    // @PutMapping(path = "{festivalName}")
    // public void updateFestival(
    //     @PathVariable("festivalName") String festivalName,
    //     @RequestParam(required = false) String location
    // ){
    //     festivalService.updateFestival(festivalName, location);
    // }
}
