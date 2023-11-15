package com.example.saywhonow_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.saywhonow_backend.models.User;
// import com.example.saywhonow_backend.models.Role;
import com.example.saywhonow_backend.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");

        // this will call database and try to find user in there
        return userRepository.findByUsername(username)
            .orElseThrow( () -> new UsernameNotFoundException("Invalid credentials"));

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    
}
