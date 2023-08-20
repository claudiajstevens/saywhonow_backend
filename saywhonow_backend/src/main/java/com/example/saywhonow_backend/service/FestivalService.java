package com.example.saywhonow_backend.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.saywhonow_backend.models.User;
import com.example.saywhonow_backend.domain.Festival;
import com.example.saywhonow_backend.models.Role;
import com.example.saywhonow_backend.repository.FestivalRepository;
import com.example.saywhonow_backend.repository.UserRepository;
import jakarta.transaction.Transactional;

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


    // TODO: make sure this has all feilds we want to add in
    public Festival addNewFestival(String festival, String location){
        return festivalRepository.save(new Festival(0, festival, location));
    }

    public void deleteFestival(Integer festivalId){
        boolean exists = festivalRepository.existsById(festivalId);

        if(!exists){
            throw new IllegalStateException("Festival with id: " + festivalId + " does not exist");
        }
        festivalRepository.deleteById(festivalId);
    }

    @Transactional
    public void updateFestival(String festivalName, String location){
        Festival festival = festivalRepository.findByName(festivalName)
            .orElseThrow( () -> new IllegalStateException(
                "festival with id: " + festivalName + " does not exist")
            );

        if(location != null && location.length() > 0 && !Objects.equals(festival.getLocation(), location)){
            festival.setLocation(location);
        }
        
    }
    
} 