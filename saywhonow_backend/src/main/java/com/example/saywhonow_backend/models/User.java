package com.example.saywhonow_backend.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    // @Column(name="user_id")
    private Integer userId;
        @Column(unique=true)
    private String username;
    private String password;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="user_role_junction",
        joinColumns = {@JoinColumn(name="user_id")},
        inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;

    public User(){
        super();
        this.authorities = new HashSet<>();
    }

    public User(Integer userId, String username, String password, Set<Role> authorities){
        super();
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    // private List<Festival> festivals = new ArrayList<>();
    // private List<String> genres = new ArrayList<>();
    // private List<Artist> artists = new ArrayList<>();
    // private List<Authority> authorities = new ArrayList<>();

    public Integer getUserId() {
        return this.userId;
    }

    public void setId(Integer userId) {
        this.userId = userId;
    }

    public void setAuthorites(Set<Role> authorities){
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return this.authorities;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // public List<Festival> getFestivals() {
    //     return festivals;
    // }

    // public void setFestivals(List<Festival> festivals) {
    //     this.festivals = festivals;
    // }

    // public List<String> getGenres() {
    //     return genres;
    // }

    // public void setGenres(List<String> genres) {
    //     this.genres = genres;
    // }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
        

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    // public static Object withDefaultPasswordEncoder() {
    //     return null;
    // }


    // public List<Artist> getArtists() {
    //     return artists;
    // }

    // public void setArtists(List<Artist> artists) {
    //     this.artists = artists;
    // }
}
