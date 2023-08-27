package com.example.saywhonow_backend.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.example.saywhonow_backend.domain.Artist;
import com.example.saywhonow_backend.repository.ArtistRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArtistService {
    
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository){
        this.artistRepository = artistRepository;
    }

    public List<Artist> getArtists(){
        return artistRepository.findAll();
    }

    public Artist addNewArtist(String artist){
        return artistRepository.save(new Artist(0, artist));
    }

    public void saveArtists(List<Artist> artists){
        artistRepository.saveAll(artists);
    }

    public List<Artist> readArtistsFromCSV(MultipartFile csvFile) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))){
            CsvToBean<Artist> csvToBean = new CsvToBeanBuilder<Artist>(reader)
                .withType(Artist.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

            return csvToBean.parse();
        }
    }

    public void deleteArtist(Integer artistId){
        boolean exists = artistRepository.existsById(artistId);

        if(!exists){
            throw new IllegalStateException("Artist with id: " + artistId + " does not exist");
        }

        artistRepository.deleteById(artistId);
    }
}
