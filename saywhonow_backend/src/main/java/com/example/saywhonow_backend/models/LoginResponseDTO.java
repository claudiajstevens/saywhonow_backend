package com.example.saywhonow_backend.models;

public class LoginResponseDTO {

    private User user;
    private String accessToken;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(User user, String accessToken){
        this.user = user;
        this.accessToken = accessToken;
    }
    
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
