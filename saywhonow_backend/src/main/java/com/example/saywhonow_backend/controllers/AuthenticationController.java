package com.example.saywhonow_backend.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.saywhonow_backend.exceptions.EmailExistsException;
import com.example.saywhonow_backend.exceptions.UsernameExistsException;
import com.example.saywhonow_backend.models.LoginResponseDTO;
import com.example.saywhonow_backend.models.RefreshRequestDTO;
import com.example.saywhonow_backend.models.RegistrationDTO;
import com.example.saywhonow_backend.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
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
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody RegistrationDTO body, HttpServletResponse response){
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(body.getUsername(), body.getPassword(), response);
        return ResponseEntity.ok().body(loginResponseDTO);
    }

    // @PostMapping("/refresh")
    // public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody RefreshRequestDTO refreshRequest, HttpServletResponse response) {
    //     // Validate refresh token, generate new access token, and return it
    //     // You might want to include additional security checks here
    //     String newAccessToken = authenticationService.refreshAccessToken(refreshRequest.getRefreshToken(), response);
    //     LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
    //     loginResponseDTO.setAccessToken(newAccessToken);
    //     return ResponseEntity.ok(loginResponseDTO);
    // }

    //@CookieValue(name = "jwt") 
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken, HttpServletResponse response){
        System.out.println(refreshToken);
        
        String newAccessToken = authenticationService.refreshAccessToken(refreshToken, response);
        // LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        // loginResponseDTO.setAccessToken(newAccessToken);
        System.out.println("New access token: " + newAccessToken);
        return ResponseEntity.ok(newAccessToken);
    }

}
