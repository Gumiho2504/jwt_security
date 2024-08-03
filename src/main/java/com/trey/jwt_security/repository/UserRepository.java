package com.trey.jwt_security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trey.jwt_security.user.User;

public interface UserRepository extends JpaRepository< User, Integer>{

    Optional<User> findByEmail(String email);
    
} 
