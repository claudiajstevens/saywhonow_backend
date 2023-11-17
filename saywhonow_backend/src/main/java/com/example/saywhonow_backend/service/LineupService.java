package com.example.saywhonow_backend.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

// import javax.sound.sampled.Line;

import com.example.saywhonow_backend.domain.Festival;
import com.example.saywhonow_backend.domain.Lineup;
import com.example.saywhonow_backend.models.FestivalLineupDTO;
import com.example.saywhonow_backend.models.LineupDTO;
import com.example.saywhonow_backend.repository.FestivalRepository;
import com.example.saywhonow_backend.repository.LineupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.nio.file.Path;

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

    public List<LineupDTO> getUpcomingLineups() {
        List<Lineup> lineups = lineupRepository.findUpcomingLineups();
        List<LineupDTO> lineupDTOs = new ArrayList<>();

        for (Lineup lineup : lineups) {
            String festivalName = lineup.getFestival().getName();
            LineupDTO lineupDTO = new LineupDTO(lineup.getId(), lineup.getStartDate(), lineup.getEndDate(), festivalName);
            lineupDTOs.add(lineupDTO);
        }

        return lineupDTOs;
    }

    // @Transactional
    public void saveLineupPoster(Integer lineupId, MultipartFile lineupImage) throws IOException {
        // byte[] lineupPoster = convertImageToBytes(imagePath);
        Lineup lineup = lineupRepository.findById(lineupId)
            .orElseThrow( () -> new RuntimeException("Lineup not found to save lineup poster to"));

        byte[] lineupPoster = lineupImage.getBytes();
        System.out.println("Original Image Byte Size - " + lineupPoster.length);
        lineupPoster = compressBytes(lineupPoster);
        lineup.setLineupPoster(lineupPoster);
        lineupRepository.save(lineup);
    }

    public Optional<byte[]> getlineupPoster(Integer lineupId) {
        Lineup lineup = lineupRepository.findById(lineupId)
            .orElseThrow( () -> new RuntimeException("Lineup not found to get lineup poster from"));
        
        // Optional<byte[]> lineupPoster = lineupRepository.findById(lineupId).map(Lineup::getLineupPoster); 
        byte[] lineupPoster = lineup.getLineupPoster();

        if( lineupPoster != null ) {
            lineupPoster = decompressBytes(lineupPoster);
            return Optional.of(lineupPoster);
        } else {
            return Optional.empty();
        }
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        } catch ( IOException e ){
        } 

        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }


    // uncompress the image bytes before returning it to application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        try {
            while(!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe ) {

        } catch (DataFormatException e){

        }

        return outputStream.toByteArray();
    }
}
