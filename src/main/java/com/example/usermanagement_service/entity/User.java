package com.example.usermanagement_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;  // Use javax.persistence.Id for relational databases like JPA

@Entity
public class User {

    @Id  // Ensure this is from javax.persistence
    private String cin;  // CIN will act as the primary key
    private String email;
    private String password;

    public User() {}

    public User(String cin, String email, String password) {
        this.cin = cin;
        this.email = email;
        this.password = password;
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
}
