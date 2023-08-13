package com.example.saywhonow_backend.service;

import java.util.HashSet;
import java.util.Set;

import com.example.saywhonow_backend.Role;
import com.example.saywhonow_backend.domain.User;
import com.example.saywhonow_backend.repository.RoleRepository;
import com.example.saywhonow_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService {
    

    // TODO: might want to change to constructor injection later
    @Autowired
    private UserRepository userRepository;

    // when creating a user also look for a role(s) to attach to user
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // TODO: create user dto to pass user info over instead of passing over authenticated password
    // or over password in User class put "@JSONIgnore"
    public User registerUser(String username, String password){

        // take password and make sure it is encoded before putting on database
        // user passwordEncoder with encode method this will give us encoded password
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);
        
        return userRepository.save(new User(0L, username, encodedPassword, authorities));
    }

}
