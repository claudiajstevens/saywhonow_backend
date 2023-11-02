package com.example.saywhonow_backend.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sound.sampled.Line;

import com.example.saywhonow_backend.domain.Festival;
import com.example.saywhonow_backend.domain.Lineup;
import com.example.saywhonow_backend.models.FestivalLineupDTO;
import com.example.saywhonow_backend.repository.FestivalRepository;
import com.example.saywhonow_backend.repository.LineupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class LineupService {
    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private LineupRepository lineupRepository;

    @Autowired
    public LineupService(LineupRepository lineupRepository){
        this.lineupRepository = lineupRepository;
    }

    public Lineup getLineup(String festival){
        return lineupRepository.findByFestival(festival);
    }

    public Lineup addNewLineup(Festival festival, Date start, Date end){
        return lineupRepository.save(new Lineup(0, festival, start, end));
    }

    public void deleteLineup(Integer lineupId){
        boolean exists = lineupRepository.existsById(lineupId);

        if(!exists){
            throw new IllegalStateException("Lineup with id: " + lineupId + " does not exist");
        }

        lineupRepository.deleteById(lineupId);
    }

    public List<Lineup> readLineupsFromCSV(MultipartFile csvFile) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))){
            CsvToBean<Lineup> csvToBean = new CsvToBeanBuilder<Lineup>(reader)
                .withType(Lineup.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

            return csvToBean.parse();
        }
        
    }

    public void saveLineups(List<Lineup> lineups) {
        lineupRepository.saveAll(lineups);
    }

    public List<Lineup> getAllLineups() {
        return lineupRepository.findAll();
    }

    public void saveFestivalLineups(List<Lineup> lineups) {
        lineupRepository.saveAll(lineups);
    }

    public void importFestivalLineups(List<FestivalLineupDTO> parsedLineupDTO) {
        List<Lineup> lineups = new ArrayList<>();
        
        for ( FestivalLineupDTO festivalLineupDTO : parsedLineupDTO ){
            // Retrieve the festival for the lineup based on the id
            System.out.println(parsedLineupDTO);
            System.out.println("Festival Id: " + festivalLineupDTO.getFestivalId());
            Integer festivalId = festivalLineupDTO.getFestivalId();
            Festival festival = festivalRepository.findById(festivalId).orElseThrow( () -> new IllegalStateException("Festival does not exist"));
            
            Lineup lineup = new Lineup();
            lineup.setFestival(festival);
            lineup.setStartDate(festivalLineupDTO.getStartDate());
            lineup.setEndDate(festivalLineupDTO.getEndDate());
            lineups.add(lineup);
        }

        lineupRepository.saveAll(lineups);

    }

    public List<Lineup> getLineupsFromFestival(Integer festivalId) {        
        return lineupRepository.findByFestivalId(festivalId);
    }

    public List<Lineup> getUpcomingLineups() {
        return lineupRepository.findUpcomingLineups();
    }
}
