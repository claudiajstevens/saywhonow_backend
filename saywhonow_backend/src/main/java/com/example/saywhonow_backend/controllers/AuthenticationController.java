package com.example.saywhonow_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.saywhonow_backend.exceptions.EmailExistsException;
import com.example.saywhonow_backend.exceptions.UsernameExistsException;
import com.example.saywhonow_backend.models.LoginResponseDTO;
import com.example.saywhonow_backend.models.RegistrationDTO;
import com.example.saywhonow_backend.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService authenticationService;

    // using registration DTO to transfer data from the request into our backend to register user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationDTO userDTO) {
        try {
            authenticationService.registerUser(userDTO);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (UsernameExistsException e) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.CONFLICT);
        } catch (EmailExistsException e) {
            return new ResponseEntity<>("Email is already registered", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
