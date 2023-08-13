package com.example.saywhonow_backend.service;

import java.util.HashSet;
import java.util.Set;

import com.example.saywhonow_backend.Role;
import com.example.saywhonow_backend.domain.User;
import com.example.saywhonow_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");

        // this will call database and try to find user in there
        return userRepository.findByUsername(username).orElseThrow( () -> UsernameNotFoundException("user is not valid"));

    }
    
}
