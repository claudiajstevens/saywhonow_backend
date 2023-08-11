package com.example.saywhonow_backend;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="festivals")
public class Festival {
    
    private Long id;
    private String name;
    private String location;
    private Lineup lineup;
    private List<String> genres;
    private int length;
    private boolean camping;
}
