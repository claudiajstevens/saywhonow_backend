package com.example.saywhonow_backend.service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.saywhonow_backend.models.User;
import com.example.saywhonow_backend.exceptions.EmailExistsException;
import com.example.saywhonow_backend.exceptions.UsernameExistsException;
import com.example.saywhonow_backend.models.LoginResponseDTO;
import com.example.saywhonow_backend.models.RegistrationDTO;
import com.example.saywhonow_backend.models.Role;
import com.example.saywhonow_backend.repository.RoleRepository;
import com.example.saywhonow_backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;

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

    // @Value("${app.jwt.refresh.secret}")
    // private String refreshTokenSecret;

    // @Value("${app.jwt.access.secret}")
    // private String accessTokenSecret;

    // Define constants for token expiration times
    private static final long ACCESS_TOKEN_EXPIRATION_SECONDS = 3600; // 1 hour
    private static final long REFRESH_TOKEN_EXPIRATION_SECONDS = 2592000; // 30 days

    // TODO: create user dto to pass user info over instead of passing over authenticated password
    // or over password in User class put "@JSONIgnore"
    // TODO: make sure that user must have username, password, and valid email
    public void registerUser(RegistrationDTO userDTO){

        if (usernameExists(userDTO.getUsername())) {
            throw new UsernameExistsException("Username is already taken");
        }

        if (emailExists(userDTO.getEmail())){
            throw new EmailExistsException("Email is already registered");
        }

        
        // take password and make sure it is encoded before putting on database
        // user passwordEncoder with encode method this will give us encoded password
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);
        
        userRepository.save(new User(0, userDTO.getUsername(), encodedPassword, userDTO.getEmail(), authorities));
    }

    private boolean usernameExists(String username){
        return userRepository.existsByUsername(username);
    }

    private boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    // the authenticationManager will look for username and password and make sure they are valid
    // then generate authentication token and send over to token service 
    public LoginResponseDTO loginUser(String username, String password, HttpServletResponse response){
        
        // when a request for login user is made, it is going to pass in username and password
        // then it be passed into authentication manager and it will use our userDetailsService
        // check to see if username exists (else throw an exception)
        // then check if password matches
        // if all valid then create jwtToken
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );        

            String accessToken = tokenService.generateJwt(auth, response);
            String refreshToken = tokenService.generateRefreshToken(auth);

            response.addCookie(tokenService.createHttpOnlyCookie("refresh_token", refreshToken));
            // might want to add .secure(true) which will mark as secure for required https
            // ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
            //     .httpOnly(true)
            //     .sameSite("None")
            //     .path("/")
            //     .maxAge(java.time.Duration.ofDays(30))

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), accessToken);

        } catch(AuthenticationException e){
            // TODO: handle exception
            System.out.println(e);
            throw new BadCredentialsException("Incorrect username or password", e);
        
            
        }
    
    }

    public String refreshAccessToken(String refreshToken, HttpServletResponse response) {
        try {
            System.out.println(refreshToken);
            Map<String, Object> refreshTokenClaims = tokenService.decodeJwt(refreshToken);
        
            // Validate the refresh token (you may check expiration, issuer, etc.)
            // Example: check expiration date
            // String issuer = (String) refreshTokenClaims.get("iss");
            // if( !"self".equals(issuer)){
            //     throw new RuntimeException("Invalid refresh token issuer");
            // }
            

            // Extract user information from the refresh token claims
            String username = (String) refreshTokenClaims.get("sub");
            System.out.println("Username: " + username);

            User userDetails = userRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("Invalid credentials or no username found"));

            // Generate a new access token for the user
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
            SecurityContextHolder.getContext().setAuthentication(auth);

            return tokenService.generateJwt(auth, response);

        } catch (JwtException e) {
            throw new RuntimeException("Error refreshing access token");
        }


    }

    // public ResponseEntity<String> refreshAccessToken(String refreshToken, ){
    //     if( refreshToken == null || refreshToken.isEmpty() ) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No refresh token found");
    //     }

    //     // Validate and extract claims for the refresh token
    //     Claims claims = Jwts.parser().setSigningKey(refreshTokenSecret).parseClaimsJws(refreshToken).getBody();

    //     // Retrieve user information based on claims (You might want to customize this based on your User model)
    //     String username = claims.get("username", String.class);
    //     List<String> roles = claims.get("roles", List.class);

    //     String newAccessToken = generateNewAccessToken(username, roles);
    //     String newRefreshToken = generateNewRefreshToken(username);

    //     Cookie cookie = new Cookie("jwt", newRefreshToken);
    //     cookie.setHttpOnly(true);
    //     cookie.setSecure(true);
    //     cookie.setSameSize("None");
    //     response.addCookie(cookie);

    //     return ResponseEntity.ok(newAccessToken);

    // }

    // private String generateNewAccessToken(String username, List<String> roles) {
    //     Instant now = Instant.now();
        
    //     return Jwts.builder()
    //             .setSubject(username)
    //             .claim("roles", roles)
    //             .setIssuedAt(now)
    //             .setExpiration(now.plusSeconds(REFRESH_TOKEN_EXPIRATION_SECONDS))
    //             .signWith(SignatureAlgorithm.HS256, accessTokenSecret)
    //             .conpact();

    // }



}
