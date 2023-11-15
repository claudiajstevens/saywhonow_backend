package com.example.saywhonow_backend.controllers;

import java.util.List;

import com.example.saywhonow_backend.models.User;
// import com.example.saywhonow_backend.service.LineupService;
import com.example.saywhonow_backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping("/")
    public String helloUserController(){
        return "User access level";
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
