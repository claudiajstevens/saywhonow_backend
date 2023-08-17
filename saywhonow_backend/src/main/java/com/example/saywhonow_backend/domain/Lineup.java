// package com.example.saywhonow_backend.domain;

// //import java.util.ArrayList;
// import java.util.Date;
// //import java.util.List;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.ManyToOne;

// @Entity
// public class Lineup {

//     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     @ManyToOne(optional=false)
//     private Festival festival;
//     private Date date;
//     // private List<Artist> artists = new ArrayList<>();
    
//     public Long getId() {
//         return id;
//     }
    
//     public void setId(Long id) {
//         this.id = id;
//     }
    
//     public Festival getFestival() {
//         return festival;
//     }
    
//     public void setFestival(Festival festival) {
//         this.festival = festival;
//     }
    
//     public Date getDate() {
//         return date;
//     }
    
//     public void setDate(Date date) {
//         this.date = date;
//     }
    
//     // public List<Artist> getArtists() {
//     //     return artists;
//     // }
    
//     // public void setArtists(List<Artist> artists) {
//     //     this.artists = artists;
//     //}
// }
