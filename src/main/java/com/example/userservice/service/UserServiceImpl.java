package com.example.userservice.service;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.User;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()))
                .toList();
    }

    @Override
    public UserResponse getUserById(int id) {
        // get the user from the db
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        // return
        return toResponse(user);
    }

    @Override
    public UserResponse createUser(UserRequest request) {

        // mapping request dto to entity
        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail());

        // save to the db
        user = userRepository.save(user);

        // return response dto
        return toResponse(user);
    }

    @Override
    public UserResponse updateUser(int id, UserRequest request) {

        // get the user from the db
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        // merge user
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        // save
        userRepository.save(user);

        // return
        return toResponse(user);
    }

    @Override
    public void deleteUser(int id) {

        // existing validation
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        // execute delete
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
