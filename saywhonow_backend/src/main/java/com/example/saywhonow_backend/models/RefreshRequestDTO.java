package com.example.saywhonow_backend.models;

public class RefreshRequestDTO {
    private String refreshToken;

    public RefreshRequestDTO(){
        
    }

    public RefreshRequestDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
