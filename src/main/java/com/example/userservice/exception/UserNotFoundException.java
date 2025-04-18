package com.example.userservice.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(int id) {
        super("User not found with id: " + id);
    }
}
