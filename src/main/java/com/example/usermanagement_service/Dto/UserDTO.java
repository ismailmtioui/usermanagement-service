// UserDTO.java
package com.example.usermanagement_service.Dto;

public class UserDTO {
    private String email;
    private String password;
    private String cin;

    public UserDTO() {}

    public UserDTO(String email, String password, String cin) {
        this.email = email;
        this.password = password;
        this.cin = cin;
    }

    // Getters and Setters
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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }
}
