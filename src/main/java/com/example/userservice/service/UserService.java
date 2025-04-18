package com.example.userservice.service;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(int id);

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(int id, UserRequest request);

    void deleteUser(int id);
}
