package com.example.saywhonow_backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    
    // @RequestMapping(value="/", method = RequestMethod.GET)
    @GetMapping("/")
    public String helloAdminController(){
        return "Admin level access";
    }
    
}
