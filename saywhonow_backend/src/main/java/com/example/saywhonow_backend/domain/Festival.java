// package com.example.saywhonow_backend.domain;

// import java.util.ArrayList;
// import java.util.List;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// @Table(name="festivals")
// public class Festival {
    
//     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     private String name;
//     private String location;
//     // private List<Lineup> lineup = new ArrayList<>();
//     private List<String> genres = new ArrayList<>();
//     private int length;
//     private boolean camping;

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getLocation() {
//         return location;
//     }

//     public void setLocation(String location) {
//         this.location = location;
//     }

//     // public List<Lineup> getLineup() {
//     //     return lineup;
//     // }

//     // public void setLineup(List<Lineup> lineup) {
//     //     this.lineup = lineup;
//     // }

//     public List<String> getGenres() {
//         return genres;
//     }

//     public void setGenres(List<String> genres) {
//         this.genres = genres;
//     }

//     public int getLength() {
//         return length;
//     }

//     public void setLength(int length) {
//         this.length = length;
//     }

//     public boolean isCamping() {
//         return camping;
//     }

//     public void setCamping(boolean camping) {
//         this.camping = camping;
//     }
// }
