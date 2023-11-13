package com.example.saywhonow_backend.exceptions;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message){
        super(message);
    }
}
