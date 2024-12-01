// User.java
package com.example.usermanagement_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    private String cin;  // CIN will act as the primary key
    private String email;
    private String password;
    private String role;  // Add the role field

    public User() {}

    public User(String cin, String email, String password, String role) {
        this.cin = cin;
        this.email = email;
        this.password = password;
        this.role = role;  // Initialize the role
    }

    // Getters and Setters
    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
