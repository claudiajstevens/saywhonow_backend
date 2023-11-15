package com.example.saywhonow_backend.controllers;

import java.io.IOException;
import java.util.List;

import com.example.saywhonow_backend.domain.Artist;
import com.example.saywhonow_backend.service.ArtistService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService){
        this.artistService = artistService;
    }

    @GetMapping
    public List<Artist> getArtists(){
        return artistService.getArtists();
    }

    @PostMapping("/add")
    public Artist registerNewArtist(String artistName){
        return artistService.addNewArtist(artistName);
    }

    @PostMapping("/import-artists")
    public String importArtists(@RequestParam("csvFile") MultipartFile csvFile){
        try {
            List<Artist> artists = artistService.readArtistsFromCSV(csvFile);

            artistService.saveArtists(artists);

            return "Artists imported successfully";
        } catch (IOException e){
            e.printStackTrace();
            return "Error importing artists: " + e.getMessage();
        }
    }

    @DeleteMapping(path = "{artistId}")
    public void deleteArtist(@PathVariable("artistId") Integer artistId){
        artistService.deleteArtist(artistId);
    }
}
