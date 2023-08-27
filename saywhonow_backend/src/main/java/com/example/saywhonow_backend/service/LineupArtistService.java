package com.example.saywhonow_backend.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.example.saywhonow_backend.domain.LineupArtist;
import com.example.saywhonow_backend.repository.LineupArtistRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LineupArtistService {
    
    @Autowired
    private LineupArtistRepository lineupArtistRepository;

    @Autowired
    public LineupArtistService(LineupArtistRepository lineupArtistRepository){
        this.lineupArtistRepository = lineupArtistRepository;
    }

    public List<LineupArtist> getLineupArtists(){
        return lineupArtistRepository.findAll();
    }

    public LineupArtist addNewLineupArtist(String artist){
        return lineupArtistRepository.save(new LineupArtist(0, artist));
    }
    
    public void saveLineupArtists(List<LineupArtist> artists){
        lineupArtistRepository.saveAll(artists);
    }

    public List<LineupArtist> readLineupArtistsFromCSV(MultipartFile csvFile) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))){
            CsvToBean<LineupArtist> csvToBean = new CsvToBeanBuilder<LineupArtist>(reader)
                .withType(LineupArtist.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
            
            return csvToBean.parse();
        }
    }

    public void deleteLineupArtist(Integer lineupArtistId){
        boolean exists = lineupArtistRepository.existsById(lineupArtistId);

        if(!exists){
            throw new IllegalStateException("Lineup Artist with id: " + lineupArtistId + " does not exist");
        }
        
        lineupArtistRepository.deleteById(lineupArtistId);
    }

    @Transactional
    public void updateLineupArtist(String artist, Date day, String stage){
        LineupArtist lineupArtist = lineupArtistRepository.findByName(artist)
            .orElseThrow( () -> new IllegalStateException("Lineup artist with name: " + artist + " does not exist")
            );

        if(day != null ){
            lineupArtist.setDate(day);
        }

        if(stage != null && stage.length() > 0 && !Objects.equals(lineupArtist.getStage(), stage)){
            lineupArtist.setStage(stage);
        }

    }
}
