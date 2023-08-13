package com.example.saywhonow_backend.controllers;

import com.example.saywhonow_backend.domain.User;
import com.example.saywhonow_backend.models.RegistrationDTO;
import com.example.saywhonow_backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@CrossOrigins()
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService authenticationService;

    // using registration DTO to transfer data from the request into our backend to register user
    @PostMapping
    public User registerUser(@RequestBody RegistrationDTO body) {
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }
}
