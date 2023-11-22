package com.example.saywhonow_backend.models;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public class RefreshRequestDTO {
    private User user;
    // private List<Role> roles;
    private String accessToken;

    public RefreshRequestDTO(){
        
    }

    public RefreshRequestDTO(User user, String accessToken) {
        this.user = user;
        // this.setRoles(roles);
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String refreshToken) {
        this.accessToken = refreshToken;
    }

    // public List<Role> getRoles() {
    //     return roles;
    // }

    // public void setRoles(List<Role> roles) {
    //     this.roles.addAll(roles);
    // }

}
