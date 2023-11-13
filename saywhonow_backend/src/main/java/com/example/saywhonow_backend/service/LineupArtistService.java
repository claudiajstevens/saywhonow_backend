package com.example.saywhonow_backend.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.saywhonow_backend.domain.Artist;
import com.example.saywhonow_backend.domain.Lineup;
import com.example.saywhonow_backend.domain.LineupArtist;
import com.example.saywhonow_backend.models.LineupArtistDTO;
import com.example.saywhonow_backend.repository.ArtistRepository;
import com.example.saywhonow_backend.repository.LineupArtistRepository;
import com.example.saywhonow_backend.repository.LineupRepository;
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
    private ArtistRepository artistRepository;

    @Autowired
    private LineupRepository lineupRepository;

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
    public void updateLineupArtist(Integer id, Date day, String stage){
        LineupArtist lineupArtist = lineupArtistRepository.findById(id)
            .orElseThrow( () -> new IllegalStateException("Lineup artist with id: " + id + " does not exist")
            );

        if(day != null ){
            lineupArtist.setDate(day);
        }

        if(stage != null && stage.length() > 0 && !Objects.equals(lineupArtist.getStage(), stage)){
            lineupArtist.setStage(stage);
        }

    }

    public void importLineupArtists(List<LineupArtistDTO> lineupArtistDTOs, Integer lineupId) {
        List<LineupArtist> lineupArtists = new ArrayList<>();
        Lineup lineup = lineupRepository.findById(lineupId).orElseThrow( () -> new NullPointerException("Lineup does not exist"));

        for( LineupArtistDTO lineupArtistDTO : lineupArtistDTOs) {
            System.out.println(lineupArtistDTO);
            LineupArtist lineupArtist = new LineupArtist();
            lineupArtist.setArtistName(lineupArtistDTO.getLineupArtist());
            // System.out.println(lineupArtistDTO.getDay());
            // System.out.println(lineupArtist.getDay());
            if( lineupArtistDTO.getDay() != null ){
                lineupArtist.setDay(lineupArtistDTO.getDay());
            }

            if( lineupArtistDTO.getStage() != null ){
                lineupArtist.setStage(lineupArtistDTO.getStage());
            }

            lineupArtist.setLineup(lineup);



            // Search for Artist to Connect to lineupArtist
            // String name = lineupArtistDTO.getLineupArtist();

            // String cleanedArtistName = name.replaceAll("\\(DJ SET\\)", "")
            //                         .replaceAll("\\(LIVE\\)", "")
            //                         .replaceAll("Presents:.*", "")
            //                         .trim();

            // List<String> splitArtists = new ArrayList<>();

            // System.out.println(cleanedArtistName);

            // if( cleanedArtistName.contains("b2b") ){
            //     String[] splitNames = cleanedArtistName.split("b2b");
            //     for( String n : splitNames ) {
            //         splitArtists.add(n.trim());
            //     }

            // }else{
            //     splitArtists.add(cleanedArtistName);
            // }

            // for ( String artistName : splitArtists ){
            //     System.out.println(artistName);
            //     Artist artist = artistRepository.findArtistByName(artistName);

            //     // if artist does not exist add it to database
            //     if (artist == null && artistName != null ){
            //         System.out.println("Artist not found");
            //         Artist newArtist = new Artist(artistName);
            //         artist = artistRepository.save(newArtist);
            //     }
                
            //     // connect artist to lineup artist
            //     lineupArtist.setArtist(artist);                
            // }


            lineupArtists.add(lineupArtist);
        }

        lineupArtistRepository.saveAll(lineupArtists);
    }

    public List<LineupArtist> getLineupArtistsInFestivalYear(Integer lineupId) {
        Lineup lineup = lineupRepository.getReferenceById(lineupId);

        return lineup.getArtists();
    }
}
