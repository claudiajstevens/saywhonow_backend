package com.example.saywhonow_backend.models;

public class RegistrationDTO {
    private String username;
    private String password;
    private String email;

    public RegistrationDTO() {
        super();
    }

    public RegistrationDTO(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return "Registration info: username " + this.username + " password: " + this.password + " email: " + this.email;
    }
}
