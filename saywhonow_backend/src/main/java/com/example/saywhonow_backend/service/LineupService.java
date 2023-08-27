package com.example.saywhonow_backend.service;

import java.util.Date;

import javax.sound.sampled.Line;

import com.example.saywhonow_backend.domain.Festival;
import com.example.saywhonow_backend.domain.Lineup;
import com.example.saywhonow_backend.repository.LineupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineupService {
    
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
}
