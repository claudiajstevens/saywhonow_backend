package com.example.saywhonow_backend.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.saywhonow_backend.models.User;
import com.example.saywhonow_backend.models.LoginResponseDTO;
import com.example.saywhonow_backend.models.Role;
import com.example.saywhonow_backend.repository.RoleRepository;
import com.example.saywhonow_backend.repository.UserRepository;

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

    // this will determine if we want to make a jwt token
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    // TODO: create user dto to pass user info over instead of passing over authenticated password
    // or over password in User class put "@JSONIgnore"
    // TODO: make sure that user must have username, password, and valid email
    public User registerUser(String username, String password, String email){

        // take password and make sure it is encoded before putting on database
        // user passwordEncoder with encode method this will give us encoded password
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);
        
        return userRepository.save(new User(0, username, encodedPassword, email, authorities));
    }

    // the authenticationManager will look for username and password and make sure they are valid
    // then generate authentication token and send over to token service 
    public LoginResponseDTO loginUser(String username, String password){
        
        // when a request for login user is made, it is going to pass in username and password
        // then it be passed into authentication manager and it will use our userDetailsService
        // check to see if username exists (else throw an exception)
        // then check if password matches
        // if all valid then create jwtToken
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );        

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch(AuthenticationException e){
            // TODO: handle exception
            System.out.println(e);
            throw new BadCredentialsException("Incorrect username or password", e);
        
            
            //return new LoginResponseDTO(null, "");
        }
    
    }

}
