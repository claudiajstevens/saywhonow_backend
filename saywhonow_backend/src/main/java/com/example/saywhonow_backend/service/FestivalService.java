package com.example.saywhonow_backend.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
// import java.util.Date;
// import java.util.HashSet;
// import java.util.Objects;
// import java.util.Optional;
// import java.util.Set;
// import java.io.InputStream;
// import java.io.FileReader;
// import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.saywhonow_backend.domain.Festival;
import com.example.saywhonow_backend.repository.FestivalRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
// import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import com.example.saywhonow_backend.models.User;
// import com.example.saywhonow_backend.models.Role;
// import com.example.saywhonow_backend.repository.UserRepository;
// import jakarta.transaction.Transactional;

@Service
public class FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    public FestivalService(FestivalRepository festivalRepository){
        this.festivalRepository = festivalRepository;
    }

    public List<Festival> getFestivals(){
        return festivalRepository.findAll();
    }

    public List<Festival> searchFestivals(String query) {
        return festivalRepository.findByNameStartingWithIgnoreCase(query);
    }

    public Festival getFestivalById(Integer id){
        System.out.println("In the festivals service");

        // this will call database and try to find user in there
        return festivalRepository.findById(id)
            .orElseThrow( () -> new IllegalStateException("Invalid credentials"));

    }
    
    // TODO: make sure this has all feilds we want to add in
    public Festival addNewFestival(String festival, String city, String state, String country, String monthHeld){
        return festivalRepository.save(new Festival(0, festival, city, state, country, monthHeld));
    }

    public void saveFestivals(List<Festival> festivals){
        festivalRepository.saveAll(festivals);
    }

    // read in a list of festials and parse through the csv
    public List<Festival> readFestivalsFromCSV(MultipartFile csvFile) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            CsvToBean<Festival> csvToBean = new CsvToBeanBuilder<Festival>(reader)
                .withType(Festival.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

            return csvToBean.parse();
        }
    }

    public void deleteFestival(Integer festivalId){
        boolean exists = festivalRepository.existsById(festivalId);

        if(!exists){
            throw new IllegalStateException("Festival with id: " + festivalId + " does not exist");
        }
        festivalRepository.deleteById(festivalId);
    }

    // @Transactional
    // public void updateFestival(String festivalName, String location){
    //     Festival festival = festivalRepository.findByName(festivalName)
    //         .orElseThrow( () -> new IllegalStateException(
    //             "festival with id: " + festivalName + " does not exist")
    //         );

    // }
    
} 
