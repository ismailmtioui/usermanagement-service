// UserService.java
package com.example.usermanagement_service.service;

import com.example.usermanagement_service.Dto.UserDTO;
import com.example.usermanagement_service.entity.User;
import com.example.usermanagement_service.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Listen for messages from the RabbitMQ queue and process them
    @RabbitListener(queues = "passengerQueue")
    public void receivePassengerCredentials(UserDTO userDTO) {
        // Check if the user already exists
        User existingUser = userRepository.findById(userDTO.getCin()).orElse(null);

        if (existingUser == null) {
            // Create a new user if they don't exist, set role as "USER"
            User newUser = new User(userDTO.getCin(), userDTO.getEmail(), userDTO.getPassword(), "USER");
            userRepository.save(newUser);
            System.out.println("User created: " + newUser.getCin());
        } else {
            // Optionally, update user information if necessary
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setPassword(userDTO.getPassword());
            userRepository.save(existingUser);
            System.out.println("User updated: " + existingUser.getCin());
        }
    }

    // Method to retrieve user by CIN
    public UserDTO getUserByCin(String cin) {
        User user = userRepository.findById(cin).orElse(null);
        if (user != null) {
            return new UserDTO(user.getEmail(), user.getPassword(), user.getCin());
        }
        return null;
    }

    // Create a new user
    public UserDTO createUser(UserDTO userDTO) {
        // Check if the user already exists
        if (userRepository.existsById(userDTO.getCin())) {
            return null; // User already exists
        }

        // Create a new user and save it
        User user = new User(userDTO.getCin(), userDTO.getEmail(), userDTO.getPassword(), "USER");
        userRepository.save(user);
        return new UserDTO(user.getEmail(), user.getPassword(), user.getCin());
    }

    // Update an existing user
    public UserDTO updateUser(String cin, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(cin);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            userRepository.save(user);
            return new UserDTO(user.getEmail(), user.getPassword(), user.getCin());
        }

        return null; // User not found
    }

    // Delete a user by CIN
    public boolean deleteUser(String cin) {
        if (userRepository.existsById(cin)) {
            userRepository.deleteById(cin);
            return true; // User successfully deleted
        }
        return false; // User not found
    }

    // Retrieve all users
    public Iterable<UserDTO> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        // Convert each user to UserDTO
        return StreamSupport.stream(users.spliterator(), false)
                .map(user -> new UserDTO(user.getEmail(), user.getPassword(), user.getCin()))
                .collect(Collectors.toList());
    }
}
