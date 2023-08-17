package com.example.saywhonow_backend.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired 
    private JwtEncoder jwtEncoder;
    
    @Autowired 
    private JwtDecoder jwtDecoder;

    public String generateJwt(Authentication auth){
        
        Instant now = Instant.now();

        // looping through all authorites in auth (where auth is an authentication
        // object which has all the roles from user)
        // map those authorites to Granted Authority 
        // then combine all of those into a single string  
        String scope = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
            
        // info Jwt will hold itself
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .subject(auth.getName())
            .claim("roles", scope)
            .build();

        // using jwt encoder to encode the Jwt Token
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();        
    }

}
