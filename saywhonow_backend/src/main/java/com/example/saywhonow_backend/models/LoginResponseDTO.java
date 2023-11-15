package com.example.saywhonow_backend.models;

public class LoginResponseDTO {

    private User user;
    private String accessToken;
    private String refreshToken;


    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(User user, String accessToken, String refreshToken){
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
