package com.example.usermanagement_service.repository;


import com.example.usermanagement_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByCin(String cin);
}
