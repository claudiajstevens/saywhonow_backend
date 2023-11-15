package com.example.saywhonow_backend.service;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;


@Service
public class TokenService {

    @Autowired 
    private JwtEncoder jwtEncoder;
    
    @Autowired 
    private JwtDecoder jwtDecoder;

    // Define constants for token expiration times
    private static final long ACCESS_TOKEN_EXPIRATION_SECONDS = 3600; // 1 hour
    private static final long REFRESH_TOKEN_EXPIRATION_SECONDS = 2592000; // 30 days

    public String generateJwt(Authentication auth, HttpServletResponse response){
        
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
            .expiresAt(now.plusSeconds(ACCESS_TOKEN_EXPIRATION_SECONDS))
            .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); 

        // Setting the access token as an HTTP-only cookie
        response.addCookie(createHttpOnlyCookie("access_token", accessToken));

        // using jwt encoder to encode the Jwt Token
        return accessToken;
    }

    // generating refresh token
    public String generateRefreshToken(Authentication auth){
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .subject(auth.getName())
            .expiresAt(now.plusSeconds(REFRESH_TOKEN_EXPIRATION_SECONDS))
            .build();

            return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    // Method to validate and decode a JWT token
    public Map<String, Object> decodeJwt(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getClaims();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    // Helper method to create an HTTP-only cookie
    private Cookie createHttpOnlyCookie(String name, String value){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60 * 1000);
        // cookie.setDomain("localhost");
        // cookie.setSecure(true);
        return cookie;
    }


}
