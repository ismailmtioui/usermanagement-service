
// UserService.java
package com.example.usermanagement_service.service;

import com.example.usermanagement_service.Dto.UserDTO;
import com.example.usermanagement_service.entity.User;
import com.example.usermanagement_service.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
