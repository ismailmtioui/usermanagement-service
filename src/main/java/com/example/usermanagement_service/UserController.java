package com.example.usermanagement_service;

import com.example.usermanagement_service.Dto.UserDTO;
import com.example.usermanagement_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);

        if (createdUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // User already exists
        }

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Get user by CIN
    @GetMapping("/{cin}")
    public ResponseEntity<UserDTO> getUserByCin(@PathVariable String cin) {
        UserDTO user = userService.getUserByCin(cin);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Update an existing user
    @PutMapping("/{cin}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String cin, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(cin, userDTO);

        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
        }

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Delete a user by CIN
    @DeleteMapping("/{cin}")
    public ResponseEntity<Void> deleteUser(@PathVariable String cin) {
        boolean isDeleted = userService.deleteUser(cin);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successfully deleted
    }

    // Get all users
    @GetMapping
    public ResponseEntity<Iterable<UserDTO>> getAllUsers() {
        Iterable<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
